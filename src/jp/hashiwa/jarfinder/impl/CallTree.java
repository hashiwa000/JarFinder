package jp.hashiwa.jarfinder.impl;

import java.io.PrintWriter;
import java.util.*;

/**
 * Created by Hashiwa on 2015/04/18.
 */
class CallTree {
  private final CalleeClasses calleeClasses = new CalleeClasses();
  private final ExtendsUpwardManager manager;

  public CallTree(ExtendsUpwardManager manager) {
    this.manager = manager;
  }

  public void add(String calleeClassName, String calleeMethodName, String calleeDesc,
           String callerClassName, String callerMethodName, String callerDesc) {

    Caller caller = new Caller(callerClassName, callerMethodName, callerDesc);
    calleeClasses
            .get(calleeClassName)
            .get(calleeMethodName)
            .get(calleeDesc)
            .add(caller);
  }

  public void printOn(PrintWriter out, String calleeClassName,
                      String calleeMethodName, String calleeDesc)
  {
    CallTreePrinter printer = new CallTreePrinter(out);
    printer.print(calleeClassName, calleeMethodName, calleeDesc);
  }

  private static class CalleeClasses extends CalleeEntries<CalleeMethods> {
    @Override
    protected CalleeMethods newEntriesInstance() {
      return new CalleeMethods();
    }
  }
  private static class CalleeMethods extends CalleeEntries<CallerDescs> {
    @Override
    protected CallerDescs newEntriesInstance() {
      return new CallerDescs();
    }
  }
  private static class CallerDescs extends CalleeEntries<Set<Caller>> {
    @Override
    protected Set<Caller> newEntriesInstance() {
      return new TreeSet<>();
    }
  }

  private static abstract class CalleeEntries<T> {
    private Map<String, T> map = new TreeMap<>();

    T get(String label) {
      T entries = map.get(label);
      if (entries == null) {
        entries = newEntriesInstance();
        map.put(label, entries);
      }
      return entries;
    }
    protected abstract T newEntriesInstance();
  }

  private static class Caller implements Comparable {
    private String clazz;
    private String method;
    private String desc;

    Caller(String clazz, String method, String desc) {
      this.clazz = clazz;
      this.method = method;
      this.desc = desc;
    }

    @Override
    public int hashCode() {
      return clazz.hashCode() +
              method.hashCode() +
              desc.hashCode();
    }

    @Override
    public boolean equals(Object o) {
      if (!(o instanceof Caller)) return false;

      Caller other = (Caller)o;
      return clazz.equals(other.clazz) &&
              method.equals(other.method) &&
              desc.equals(other.desc);
    }

    @Override
    public int compareTo(Object o) {
      if (!(o instanceof Caller)) return 0;

      Caller other = (Caller)o;
      int ret;

      ret = clazz.compareTo(other.clazz);
      if (ret != 0) return ret;

      ret = method.compareTo(other.method);
      if (ret != 0) return ret;

      return desc.compareTo(other.desc);
    }
  }

  private class CallTreePrinter {
    private final PrintWriter out;
    private final Set<Caller> doneSet = new HashSet<>();

    CallTreePrinter(PrintWriter out) {
      this.out = out;
    }

    public void print(String calleeClassName, String calleeMethodName,
                        String calleeDesc)
    {
      print0(calleeClassName, calleeMethodName,
              calleeDesc, 0);
    }

    private void print0(String calleeClassName, String calleeMethodName,
               String calleeDesc, int depth)
    {
      // reject loop
      if (isDone(calleeClassName, calleeMethodName, calleeDesc)) return;

      Set<Caller> callers = calleeClasses
              .get(calleeClassName)
              .get(calleeMethodName)
              .get(calleeDesc);

      printOneCaller(out, calleeClassName,
              calleeMethodName, calleeDesc, depth);

      for (Caller caller: callers) {
        int newDepth = depth + 1;
        String callerClassName = caller.clazz;
        String callerMethodName = caller.method;
        String callerDesc = caller.desc;
        print0(callerClassName, callerMethodName,
                callerDesc, newDepth);
      }

      String superClassName = manager.getSuperClass(calleeClassName);
      callers = calleeClasses
              .get(superClassName)
              .get(calleeMethodName)
              .get(calleeDesc);

      for (Caller caller: callers) {
        int newDepth = depth + 1;
        String callerClassName = caller.clazz;
        String callerMethodName = caller.method;
        String callerDesc = caller.desc;
        print0(callerClassName, callerMethodName,
                callerDesc, newDepth);
      }
    }

    private void printOneCaller(PrintWriter out, String calleeClassName,
                                String calleeMethodName, String calleeDesc,
                                int depth)
    {
      if (depth != 0) {
        for (int i=0 ; i<depth ; i++) out.print('\t');
        out.print("<-");
      }
      out.println(calleeClassName + "." + calleeMethodName + ":" + calleeDesc);
    }

    private boolean isDone(String clazz, String method, String desc) {
      Caller caller = new Caller(clazz, method, desc);

      if (doneSet.contains(caller)) return true;

      doneSet.add(caller);
      return false;
    }
  }
}
