package jp.hashiwa.jarfinder.impl;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import jp.hashiwa.jarfinder.impl.ConstantPoolEntry.Utf8_info;

public class ConstantPoolFilterProcessor extends JarFileProcessorImpl {
  private static boolean DEBUG = true;
  private final String containsStrPattern;
  
  public ConstantPoolFilterProcessor(String containsStrPattern, 
      PrintWriter out) {
    super(out);
    this.containsStrPattern = containsStrPattern;
  }
  
  @Override
  protected void processOneClassEntry(ZipFile f, ZipEntry e) {
    println("----- " + e.getName() + " -----");
    
    byte[] bytes = new byte[4];
    try (DataInputStream in = new DataInputStream(
        new BufferedInputStream(f.getInputStream(e)))) {
//      in.readFully(bytes, 0, 4);
//      System.out.println(
//          toHex(bytes[0]) + "," + 
//          toHex(bytes[1]) + "," + 
//          toHex(bytes[2]) + "," + 
//          toHex(bytes[3]));
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
        if (containsStrPattern==null || 
            str.contains(containsStrPattern)) {
          println(str);
        }
      }
    }
  }
  
  private String toHex(int b) {
    return "0x" + Integer.toHexString(0x000000ff & b);
  }
  
  private int b2i(byte b) {
    return 0x000000ff & b;
  }
}
