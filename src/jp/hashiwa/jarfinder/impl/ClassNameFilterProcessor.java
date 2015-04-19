package jp.hashiwa.jarfinder.impl;

import java.io.PrintWriter;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ClassNameFilterProcessor extends JarFileProcessorImpl {
  private final Pattern pattern;
  
  public ClassNameFilterProcessor(String targetRegex, 
      PrintWriter out, boolean verbose) {
    super(out, verbose);
    this.pattern = targetRegex==null ?
        null : Pattern.compile(targetRegex);
  }
  
  @Override
  protected void processOneClassEntry(ZipFile f, ZipEntry e) {
    String name = e.getName();
    String classFileName = name.substring(name.lastIndexOf('/') + 1);
    if (pattern==null || 
        pattern.matcher(classFileName).matches()) {
      consumeJarFileStr();
      println(e.getName());
    }
  }

  public void doStart() {}
  public void doEnd() {}
}
