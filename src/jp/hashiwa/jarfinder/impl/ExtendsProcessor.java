package jp.hashiwa.jarfinder.impl;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by Hashiwa on 2015/04/23.
 */
public class ExtendsProcessor extends JarFileProcessorImpl {
  private final ExtendsManager manager = new ExtendsManager();
  private final ClassVisitor cv = new ExtendsClassVisitor(manager);

  public ExtendsProcessor(PrintWriter out, boolean verbose) {
    super(out, verbose);
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
  public void doStart() {

  }

  @Override
  public void doEnd() {
    manager.printOn(getOut());
  }
}
