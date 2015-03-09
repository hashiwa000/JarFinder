package jp.hashiwa.jarfinder.impl;

import java.io.PrintWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ClassNameFilterProcessor extends JarFileProcessorImpl {
  private final String containsStrPattern;
  
  public ClassNameFilterProcessor(String containsStrPattern, 
      PrintWriter out) {
    super(out);
    this.containsStrPattern = containsStrPattern;
  }
  
  @Override
  protected void processOneClassEntry(ZipFile f, ZipEntry e) {
    String name = e.getName();
    String classFileName = name.substring(name.lastIndexOf('/') + 1);
    if (containsStrPattern==null || 
        classFileName.contains(containsStrPattern)) {
    
      println(e.getName());
    }
  }
}
