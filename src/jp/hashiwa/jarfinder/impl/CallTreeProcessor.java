package jp.hashiwa.jarfinder.impl;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by Hashiwa on 2015/04/17.
 */
public class CallTreeProcessor extends JarFileProcessorImpl {
  private final CallTree callTree = new CallTree();
  private final ClassVisitor cv = new CallTreeClassVisitor(callTree);

  // e.g. "jp/hashiwa/tp/B.xxx:()V";
  private final String methodDesc;

  public CallTreeProcessor(PrintWriter out, String methodDesc, boolean verbose) {
    super(out, verbose);
    this.methodDesc = methodDesc;
  }

  @Override
  protected void processOneClassEntry(ZipFile f, ZipEntry e) {
    try {
      ClassReader cr = new ClassReader(f.getInputStream(e));
      cr.accept(cv, 0);

    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }

  public void doStart() {}

  public void doEnd() {
    int clsEndIndex = methodDesc.indexOf('.');
    int methodEndIndex = methodDesc.indexOf(':');

//    String cls = "jp/hashiwa/tp/B";
//    String method = "xxx";
//    String desc = "()V";

    String cls = methodDesc.substring(0, clsEndIndex);
    String method = methodDesc.substring(clsEndIndex+1, methodEndIndex);
    String desc = methodDesc.substring(methodEndIndex+1);

    callTree.printOn(getOut(), cls, method, desc);
  }
}
