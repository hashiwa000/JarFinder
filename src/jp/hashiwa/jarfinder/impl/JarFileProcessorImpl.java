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
   * �T������jar�t�@�C���p�X�B
   * ��verbose���[�h(verbose==false)�̂Ƃ��ɁAjar�t�@�C���p�X����ŕ\�����邽�߂Ɏg�p�����B
   * verbose���[�h(verbose==true)�̂Ƃ��́A���null�ƂȂ�B
   * {@link #produceJarFileStr(String)}�@���Ăяo���ƁA��verbose���[�h�̏ꍇ��
   * jar�t�@�C���p�X����������B
   * {@link #consumeJarFileStr()} ���Ăяo���ƁA����Ăяo�����Ɍ���A
   * ��verbose���[�h�̏ꍇ��jar�t�@�C���p�X���\�������B
   * 
   */
  private String cachedJarPath;
  
  public JarFileProcessorImpl(PrintWriter out, boolean verbose) {
    this.out = out;
    this.verbose = verbose;
  }

  public void doOneJar(String jarPath) {
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

  protected PrintWriter getOut() {
    return out;
  }

  protected void println(String str) {
    out.println(str);
  }
  
  protected boolean isVerbose() {
    return verbose;
  }
  
  protected void produceJarFileStr(String jarPathStr) {
//    assert jarPathStr!=null;
    if (verbose) {
      printJarPath(jarPathStr);
    } else {
      cachedJarPath = jarPathStr;
    }
  }
  
  protected void consumeJarFileStr() {
    if (cachedJarPath!=null && !verbose) {
      printJarPath(cachedJarPath);
      cachedJarPath = null;
    }
  }
  
  private void printJarPath(String jarPath) {
    println("--- " + jarPath + " ---");
  }
  
  protected abstract void processOneClassEntry(ZipFile f, ZipEntry e);
}
