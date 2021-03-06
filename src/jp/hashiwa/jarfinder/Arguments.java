package jp.hashiwa.jarfinder;

import jp.hashiwa.jarfinder.impl.*;

import java.io.File;
import java.io.PrintWriter;
import java.util.stream.Stream;

/**
 * アプリケーションの引数解析、初期設定を行うクラス。
 * @author Hashiwa
 */
class Arguments {
  private String command;
  private String[] dirs;
  private JarFileProcessor proc;
  
  public String getCommand() {
    return command;
  }
  public String[] getDirs() {
    return dirs;
  }
  public JarFileProcessor getProcessor() {
    return proc;
  }
  
  Arguments(String[] args, PrintWriter out) {
    if (args.length<2) usage();
    
    this.command = args[0];
    this.dirs = splitPaths(args[1]);
    
    switch(this.command) {
    case "class":
      parseClassArgs(args, out);
      break;
    case "class-all":
      parseAllClassArgs(args, out);
      break;
    case "constpool":
      parseConstantPoolArgs(args, out);
      break;
    case "constpool-all":
      parseAllConstantPoolArgs(args, out);
      break;
    case "calltree":
      parseCallTreeArgs(args, out);
      break;
    case "extends":
        parseExtendsArgs(args, out);
        break;
    case "methods":
        parseMethodsArgs(args, out);
        break;
    default:
      usage();
    }
  }
  private void parseClassArgs(String[] args, PrintWriter out) {
    if (4 < args.length) usage();

    String className = args[2];
    boolean verbose = false;
    if (args.length == 4) {
      if ("-v".equals(args[3])) verbose = true;
      else usage();
    }
    
    this.proc = new ClassNameFilterProcessor(className, out, verbose);
  }
  
  private void parseAllClassArgs(String[] args, PrintWriter out) {
    this.proc = new ClassNameFilterProcessor(null, out, true);
  }
  
  private void parseConstantPoolArgs(String[] args, PrintWriter out) {
    if (4 < args.length) usage();
    
    String className = args[2];
    boolean verbose = false;
    if (args.length == 4) {
      if ("-v".equals(args[3])) verbose = true;
      else usage();
    }
    
    this.proc = new ConstantPoolFilterProcessor(className, out, verbose);
  }
  
  private void parseAllConstantPoolArgs(String[] args, PrintWriter out) {
    if (2 < args.length) usage();
    this.proc = new ConstantPoolFilterProcessor(null, out, true);
  }

  private void parseCallTreeArgs(String[] args, PrintWriter out) {
    if (args.length != 3) usage();

    String methodDesc = args[2];

    this.proc = new CallTreeProcessor(out, methodDesc, false);
  }

  private void parseExtendsArgs(String[] args, PrintWriter out) {
    if (2 < args.length) usage();
    this.proc = new ExtendsProcessor(out, false);
  }

  private void parseMethodsArgs(String[] args, PrintWriter out) {
    if (2 < args.length) usage();
    this.proc = new MethodsProcessor(out, false);
  }

  private static String[] splitPaths(String path) {
    final String separator = String.valueOf(File.pathSeparatorChar);
    String[] ret = Stream.of(path.split(separator))
            .filter(s -> !s.equals(""))
            .toArray(n -> new String[n]);

    return ret;
  }

  private static boolean isWindows() {
    return System.getProperty("os.name", "").contains("Windows");
  }

  private static void usage() {
    System.out.println("Usage : java -jar jarfinder.jar <command> [options]");
    System.out.println();
    System.out.println("<command>");
    System.out.println("  class or class-all or constpool or constpool-all or");
    System.out.println("  calltree or extends or methods");
    System.out.println();
    System.out.println("<command>      : [options]");
    System.out.println("  class        : <dir> <target-class> [-v]");
    System.out.println("    Search classes in jar files.");
    System.out.println("    <dir> are start directories for finding jar files, or");
    System.out.println("    jar file paths");
    System.out.println("    <target-class> is regex expression of class name");
    System.out.println("    which you are looking for. If not specified, all");
    System.out.println("    classes are shown.");
    System.out.println("    [-v] is flag to enable verbose mode. Default is");
    System.out.println("    disabled.");
    System.out.println("  class-all    : <dir>");
    System.out.println("    Show all classes in jar files.");
    System.out.println("    <dir> are start directories for finding jar files, or");
    System.out.println("    jar file paths");
    System.out.println("  constpool    : <dir> <target-str> [-v]");
    System.out.println("    Search constant pool entries (UTF8 info) in jar files.");
    System.out.println("    <dir> are start directories for finding jar files, or");
    System.out.println("    jar file paths");
    System.out.println("    <target-str> is regex expression of constant pool");
    System.out.println("    entry (UTF8 info) which you are looking for.");
    System.out.println("    [-v] is flag to enable verbose mode. Default is");
    System.out.println("    disabled.");
    System.out.println("  constpool-all : <dir>");
    System.out.println("    Show all constant pool entry (UTF8 info).");
    System.out.println("    <dir> are start directories for finding jar files, or");
    System.out.println("    jar file paths");
    System.out.println("  calltree : <dir> <target-class>");
    System.out.println("    Show call tree.");
    System.out.println("    <dir> are start directories for finding jar files, or");
    System.out.println("    jar file paths");
    System.out.println("    <target-method> is target callee method. (e.g. ");
    System.out.println("    jp/hashiwa/tp/B.xxx:()V)");
    System.out.println("    (under development...)");
    System.out.println("  extends : <dir>");
    System.out.println("    Show extends/implements relationships.");
    System.out.println("    <dir> are start directories for finding jar files, or");
    System.out.println("    jar file paths");
    System.out.println("    NOTE: <dir> must contain rt.jar in JDK/JRE.");
    System.out.println("  methods : <dir>");
    System.out.println("    Show all methods.");
    System.out.println("    <dir> are start directories for finding jar files, or");
    System.out.println("    jar file paths");
    System.out.println();
    System.out.println("<dir> is ; or : separated string.");
    System.out.println("(see API Reference of java.io.File#pathSeparatorChar)");
    System.out.println("  e.g. C:\\tmpdir;a.jar  (Windows)");
    System.out.println("  e.g. /tmp/tmpdir:b.jar (Unix/Linux)");
    
    System.exit(-1);
  }
}
