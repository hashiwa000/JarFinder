package jp.hashiwa.jarfinder.impl;

import com.sun.istack.internal.NotNull;

import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by Hashiwa on 2015/04/23.
 */
class ExtendsDownwardManager extends ExtendsManager {

  @Override
  public void addSuperClass(@NotNull String subClass, @NotNull String superClass) {
    super.addExtendsValue(superClass, subClass);
  }

  @Override
  public void addInterface(@NotNull String cls, @NotNull String itf) {
    super.addImplementsValue(itf, cls);
  }

  void printOn(@NotNull PrintWriter out) {
    out.println("---------- extends ----------");
    printExtendsOn(out, "java/lang/Object", 0);
    out.println();
    out.println("-------- implements ---------");
    printImplementsOn(out);
  }

  private void printExtendsOn(PrintWriter out, String cls, int indent) {
    for (int i=0 ; i<indent ; i++) out.print('\t');
    if (indent != 0) out.print("<-");
    out.println(cls);

    Set<String> subClasses = getExtendsValue(cls);
    int nextIndent = indent + 1;

    if (subClasses == null) return;
    subClasses.stream()
            .filter(c -> c != null)
            .forEach(c -> printExtendsOn(out, c, nextIndent));
  }

  private void printImplementsOn(PrintWriter out) {
    getImplementsMap().forEach(
            (itf, classes) -> {
              out.println(itf);
              classes.stream()
                      .filter(c -> c != null)
                      .forEach(c -> out.println("\t<=" + c));
            }
    );
  }
}
