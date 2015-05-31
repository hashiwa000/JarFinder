package jp.hashiwa.jarfinder.impl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import jp.hashiwa.jarfinder.JarFileProcessor;

public abstract class JarFileProcessorImpl implements JarFileProcessor {
  private final PrintWriter out;
  private final boolean verbose;
  
  /**
   * 探索中のjarファイルパス。
   * 非verboseモード(verbose==false)のときに、jarファイルパスを後で表示するために使用される。
   * verboseモード(verbose==true)のときは、常にnullとなる。
   * {@link #produceJarFileStr(String)}　を呼び出すと、非verboseモードの場合は
   * jarファイルパスが代入される。
   * {@link #consumeJarFileStr()} を呼び出すと、初回呼び出し時に限り、
   * 非verboseモードの場合はjarファイルパスが表示される。
   * 
   */
  private String cachedJarPath;
  
  public JarFileProcessorImpl(PrintWriter out, boolean verbose) {
    this.out = out;
    this.verbose = verbose;
  }

  public final void doOneJar(String jarPath) {
    produceJarFileStr(jarPath);

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

  protected final PrintWriter getOut() {
    return out;
  }

  protected final void println(String str) {
    out.println(str);
  }
  
  protected final boolean isVerbose() {
    return verbose;
  }
  
  protected final void produceJarFileStr(String jarPathStr) {
//    assert jarPathStr!=null;
    if (verbose) {
      printJarPath(jarPathStr);
    } else {
      cachedJarPath = jarPathStr;
    }
  }
  
  protected final void consumeJarFileStr() {
    if (cachedJarPath!=null && !verbose) {
      printJarPath(cachedJarPath);
      cachedJarPath = null;
    }
  }
  
  private final void printJarPath(String jarPath) {
    println("--- " + jarPath + " ---");
  }
  
  protected abstract void processOneClassEntry(ZipFile f, ZipEntry e);
}
