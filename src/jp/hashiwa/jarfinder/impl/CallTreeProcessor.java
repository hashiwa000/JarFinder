package jp.hashiwa.jarfinder.impl;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by Hashiwa on 2015/04/17.
 */
public class CallTreeProcessor extends JarFileProcessorImpl {
  private final CallTree callTree = new CallTree();
  private final ClassVisitor cv = new CallTreeClassVisitor(callTree);

  public CallTreeProcessor(PrintWriter out, boolean verbose) {
    super(out, verbose);
    init();
  }

  public void init() {
  }

  @Override
  protected void processOneClassEntry(ZipFile f, ZipEntry e) {
    // produceClassName(e.getName());

    try {
      ClassReader cr = new ClassReader(f.getInputStream(e));
      cr.accept(cv, 0);

    // } catch (EOFException e1) {
    //   e1.printStackTrace();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }

  public void doStart() { init(); }
  public void doEnd() {
    // String target = "jp.hashiwa.tp.b.xxx:()V";
    String cls = "jp.hashiwa.tp.b";
    String method = "xxx";
    String desc = "()V";
    callTree.printOn(getOut(), cls, method, desc);
  }
}
