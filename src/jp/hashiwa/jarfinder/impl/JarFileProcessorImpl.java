package jp.hashiwa.jarfinder.impl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import jp.hashiwa.jarfinder.JarFileProcessor;

public abstract class JarFileProcessorImpl implements JarFileProcessor {
  private final PrintWriter out;
  
  public JarFileProcessorImpl(PrintWriter out) {
    this.out = out;
  }

  public void doOneJar(String jarPath) {
    println("--- " + jarPath + " ---");

    File file = new File(jarPath);

    try {
      ZipFile zip = new ZipFile(file);
      
      zip.stream()
          .filter(e -> !e.isDirectory())
          .filter(e -> e.getName().endsWith(".class"))
          .forEach(e -> processOneClassEntry(zip, e));
          
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  protected void println(String str) {
    out.println(str);
  }
  
  protected abstract void processOneClassEntry(ZipFile f, ZipEntry e);
}
