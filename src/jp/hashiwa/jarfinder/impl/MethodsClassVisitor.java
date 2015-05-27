package jp.hashiwa.jarfinder.impl;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

import java.io.PrintWriter;

/**
 * Created by Hashiwa on 2015/05/07.
 */
public class MethodsClassVisitor extends ClassVisitor {
  private final PrintWriter out;
  private String className;

  public MethodsClassVisitor(PrintWriter out) {
    super(Opcodes.ASM5);
    this.out = out;
  }

  @Override
  public void visit(int version, int access, String name,
                    String signature, String superName, String[] interfaces) {
    this.className = name;
    super.visit(version, access, name, signature, superName, interfaces);
  }

  @Override
  public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
    String methodName = name;

    StringBuilder buf = new StringBuilder();
    buf.append(className).append('.')
       .append(methodName).append(':')
       .append(desc);
    out.println(buf);

    return super.visitMethod(access, name, desc, signature, exceptions);
  }
}
