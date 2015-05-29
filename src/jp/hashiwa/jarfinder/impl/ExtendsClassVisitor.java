package jp.hashiwa.jarfinder.impl;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

/**
 * Created by Hashiwa on 2015/04/23.
 */
public class ExtendsClassVisitor extends ClassVisitor {
  private final ExtendsManager manager;

  public ExtendsClassVisitor(ExtendsManager manager) {
    super(Opcodes.ASM5);
    this.manager = manager;
  }

  @Override
  public void visit(int version, int access, String name, String signature,
                    String superName, String[] interfaces) {

    super.visit(version, access, name, signature, superName, interfaces);
    boolean isInterface = (access & Opcodes.ACC_INTERFACE) != 0;

    if (!isInterface)
      manager.addSuperClass(name, superName);

    manager.addInterfaces(name, interfaces);
  }
}
