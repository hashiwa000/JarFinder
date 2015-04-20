package jp.hashiwa.jarfinder.impl;

import com.sun.istack.internal.NotNull;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Hashiwa on 2015/04/18.
 */
class CallTree {
  // private Map<CallTreeEntry, Set<CallTreeEntry>> callee2caller = new HashMap<>();
  private Set<CallTreeEntry> entries = new HashSet<>();

  public boolean add(String calleeClassName, String calleeMethodName, String calleeDesc,
           String callerClassName, String callerMethodName, String callerDesc) {

    return add(
            new CallTreeEntry(calleeClassName, calleeMethodName, calleeDesc),
            new CallTreeEntry(callerClassName, callerMethodName, callerDesc));
  }

  public boolean add(CallTreeEntry callee, CallTreeEntry caller) {
    CallTreeEntry calleeInEntries = null;
    CallTreeEntry callerInEntries = null;
    for (CallTreeEntry e: entries) {
      if (calleeInEntries==null && e.equals(callee)) {
        calleeInEntries = e;
      } else if (callerInEntries==null && e.equals(caller)) {
        callerInEntries = e;
      }

      if (calleeInEntries!=null && callerInEntries!=null) break;
    }

    if (calleeInEntries == null) {
      calleeInEntries = callee;
      entries.add(calleeInEntries);
    }

    if (callerInEntries == null) {
      callerInEntries = caller;
      entries.add(callerInEntries);
    }

    return calleeInEntries.addCaller(callerInEntries);
  }

  public void printOn(PrintWriter out, String calleeClassName, String calleeMethodName, String calleeDesc) {
    printOn(out, new CallTreeEntry(calleeClassName, calleeMethodName, calleeDesc));
  }
  public void printOn(PrintWriter out, CallTreeEntry callee) {
    printOn(out, callee, 0, new HashSet<CallTreeEntry>());
  }
  private void printOn(PrintWriter out, CallTreeEntry callee, int indent, Set<CallTreeEntry> alreadyPrinted) {
    CallTreeEntry root = null;
    for (CallTreeEntry e: entries) {
      if (e.equals(callee)) {
        root = e;
        break;
      }
    }

    if (root == null) {
      out.println();
      return;
    }

    for (int i=0 ; i<indent ; i++) {
      out.print('\t');
    }
    if (indent!=0) out.print("<-");

    out.println(root);

    int nextIndent = indent + 1;
    for (CallTreeEntry e: root.getCallers()) {
      if (!alreadyPrinted.contains(e)) {
        alreadyPrinted.add(e);
        printOn(out, e, nextIndent, alreadyPrinted);
      }
    }
  }

  static class CallTreeEntry {
    private String calleeClassName;
    private String calleeMethodName;
    private String calleeDesc;
    private Set<CallTreeEntry> callers;
    private String printStrCache = null;

    CallTreeEntry(@NotNull String calleeClassName, @NotNull String calleeMethodName, @NotNull String calleeDesc) {
      this.calleeClassName = calleeClassName;
      this.calleeMethodName = calleeMethodName;
      this.calleeDesc = calleeDesc;
      this.callers = new HashSet<>();
    }

    public String getClassName() {
      return calleeClassName;
    }

    public String getMethodName() {
      return calleeMethodName;
    }

    public String getDesc() {
      return calleeDesc;
    }

    public boolean addCaller(CallTreeEntry caller) {
      return callers.add(caller);
    }

    private Set<CallTreeEntry> getCallers() {
      return callers;
    }

    @Override
    public int hashCode() {
      return calleeClassName.hashCode() +
              calleeMethodName.hashCode() +
              calleeDesc.hashCode();
    }

    public boolean equals(String className, String methodName, String desc) {
      return this.calleeClassName.equals(className) &&
              this.calleeMethodName.equals(methodName) &&
              this.calleeDesc.equals(desc);
    }

    @Override
    public boolean equals(Object o) {
      if (o == null) return false;
      if (!(o instanceof CallTreeEntry)) return false;
      CallTreeEntry e = (CallTreeEntry) o;
      return equals(e.getClassName(), e.getMethodName(), e.getDesc());
    }

    @Override
    public String toString() {
      if (printStrCache == null) {
        StringBuilder sb = new StringBuilder();
        sb.append(getClassName());
        sb.append('.');
        sb.append(getMethodName());
        sb.append(':');
        sb.append(getDesc());
        printStrCache = sb.toString();
      }
      return printStrCache;
    }
  }
}
