package jp.hashiwa.jarfinder.impl;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import jp.hashiwa.jarfinder.impl.ConstantPoolEntry.Utf8_info;

public class ConstantPoolFilterProcessor extends JarFileProcessorImpl {
  private final Pattern pattern;
  
  private String cachedClassName = null;
  
  public ConstantPoolFilterProcessor(String targetRegex, 
      PrintWriter out, boolean verbose) {
    super(out, verbose);
    this.pattern = targetRegex==null ?
        null : Pattern.compile(targetRegex);
  }
  
  @Override
  protected void processOneClassEntry(ZipFile f, ZipEntry e) {
    produceClassName(e.getName());
    
    byte[] bytes = new byte[4];
    try (DataInputStream in = new DataInputStream(
        new BufferedInputStream(f.getInputStream(e)))) {
      readMagic(in, bytes);
      readVersion(in, bytes);
      readConstantPool(in, bytes);
    } catch (EOFException e1) {
      e1.printStackTrace();
    } catch (IOException e1) {
      e1.printStackTrace();
    }

  }
  
  private void readMagic(DataInputStream in, byte[] bytes)
      throws IOException {
    
    int magic = in.readInt();
    if (magic != 0xcafebabe) {
      throw new Error("not java class");
    }
  }
  
  private void readVersion(DataInputStream in, byte[] bytes)
      throws IOException {
    @SuppressWarnings("unused")
    char minor = in.readChar();
    @SuppressWarnings("unused")
    char major = in.readChar();
  }
  
  private void readConstantPool(DataInputStream in, byte[] bytes)
      throws IOException {
    int count = in.readChar();
    ConstantPoolEntry[] entries = new ConstantPoolEntry[count];
    int i=0;
    
    while (i < count-1) {
      ConstantPoolEntry e = ConstantPoolEntry.createFrom(in);
      entries[i] = e;
      i += e.getOccupyingEntryCount();
      
      if (e instanceof Utf8_info) {
        String str = ((Utf8_info) e).getActualString();
        if (pattern==null || pattern.matcher(str).matches()) {
          consumeJarFileStr();
          consumeClassName();
          println(str);
        }
      }
    }
  }
  
  private void produceClassName(String className) {
    //assert className!=null;
    if (isVerbose()) {
      printClassName(cachedClassName);
    } else {
      cachedClassName = className;
    }
  }
  
  private void consumeClassName() {
    if (cachedClassName!=null && !isVerbose()) {
      printClassName(cachedClassName);
      cachedClassName = null;
    }
  }
  
  private void printClassName(String className) {
    println("----- " + className + " -----");
  }
}
