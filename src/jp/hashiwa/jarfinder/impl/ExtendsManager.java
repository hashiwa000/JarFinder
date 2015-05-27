package jp.hashiwa.jarfinder.impl;

import com.sun.istack.internal.NotNull;

import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by Hashiwa on 2015/04/23.
 */
class ExtendsManager {
  private final Map<String, Set<String>> class2subClasses = new HashMap<>();
  private final Map<String, Set<String>> interface2classes = new TreeMap<>();

  void addSuperClass(@NotNull String subClass, @NotNull String superClass) {
    Set<String> subClasses = class2subClasses.get(superClass);
    if (subClasses == null) {
      subClasses = new TreeSet<>();
      class2subClasses.put(superClass, subClasses);
    }
    subClasses.add(subClass);
  }

  void addInterfaces(@NotNull String cls, @NotNull String[] interfaces) {
    if (interfaces == null) return;
    Stream.of(interfaces).forEach(
            i -> addInterface(cls, i)
    );
  }

  void addInterface(@NotNull String cls, @NotNull String itf) {
    if (cls == null || itf == null) return;

    Set<String> classes = interface2classes.get(itf);
    if (classes == null) {
      classes = new TreeSet<>();
      interface2classes.put(itf, classes);
    }
    classes.add(cls);
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

    Set<String> subClasses = class2subClasses.get(cls);
    int nextIndent = indent + 1;

    if (subClasses == null) return;
    subClasses.stream()
            .filter(c -> c != null)
            .forEach(c -> printExtendsOn(out, c, nextIndent));
  }

  private void printImplementsOn(PrintWriter out) {
    interface2classes.forEach(
            (itf, classes) -> {
              out.println(itf);
              classes.stream()
                      .filter(c -> c!=null)
                      .forEach(c -> out.println("\t<-" + c));
            }
    );
  }
}
