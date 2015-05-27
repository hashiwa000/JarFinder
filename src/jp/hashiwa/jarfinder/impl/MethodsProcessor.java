package jp.hashiwa.jarfinder.impl;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by Hashiwa on 2015/05/07.
 */
public class MethodsProcessor extends JarFileProcessorImpl {
  private final ClassVisitor cv;

  public MethodsProcessor(PrintWriter out, boolean verbose) {
    super(out, verbose);
    this.cv = new MethodsClassVisitor(out);
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

  @Override
  public void doStart() {}

  @Override
  public void doEnd() {}
}
