package jp.hashiwa.jarfinder.impl;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

import java.util.Map;
import java.util.Set;

/**
 * Created by Hashiwa on 2015/04/17.
 */
class CallTreeClassVisitor extends ClassVisitor {
  private final CallTree callTree;

  CallTreeClassVisitor(CallTree callTree) {
    super(Opcodes.ASM5);
    this.callTree = callTree;
  }

  private String className = null;

  @Override
  public void visit(int version, int access, String name, String signature,
                    String superName, String[] interfaces) {

    this.className = name;

    super.visit(version, access, name, signature, superName, interfaces);
  }

  @Override
  public MethodVisitor visitMethod(int access, String name, String desc, String signature,
                                   String[] exceptions) {
    final String callerClassName = this.className;
    final String callerMethodName = name;
    final String callerDesc = desc;
    return new MethodVisitor(Opcodes.ASM5) {
      @Override
      public void visitMethodInsn(int i, String owner, String name, String desc, boolean itf) {
        super.visitMethodInsn(i, owner, name, desc, itf);
        // System.out.println(callerClassName + " : " + callerMethodName + " : " + callerDesc);
        // System.out.println(owner + " : " + name + " : " + desc);
        callTree.add(owner, name, desc, callerClassName, callerMethodName, callerDesc);
      }
    };
  }
}
