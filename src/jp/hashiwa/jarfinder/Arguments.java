package jp.hashiwa.jarfinder;

import java.io.BufferedOutputStream;
import java.io.PrintWriter;

import jp.hashiwa.jarfinder.impl.ClassNameFilterProcessor;
import jp.hashiwa.jarfinder.impl.ConstantPoolFilterProcessor;

class Arguments {
  private String command;
  private String dir;
  private JarFileProcessor proc;
  
  public String getCommand() {
    return command;
  }
  public String getDir() {
    return dir;
  }
  public JarFileProcessor getProcessor() {
    return proc;
  }
  
  Arguments(String[] args) {
    if (args.length<2) usage();
    
    PrintWriter out = new PrintWriter(
        new BufferedOutputStream(System.out));
    
    this.command = args[0];
    this.dir = args[1];
    
    switch(this.command) {
    case "class":
      parseClassArgs(args, out);
      break;
    case "constpool":
      parseConstantPoolArgs(args, out);
      break;
    case "constpool-all":
      parseAllConstantPoolArgs(args, out);
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
    this.proc = new ConstantPoolFilterProcessor(null, out, true);
  }
  
  private static void usage() {
    System.out.println("java -jar xxx.jar <command> [options]");
    System.out.println();
    System.out.println("<command>");
    System.out.println("  class : find class from jar files.");
    System.out.println("  constpool : find string in all constant pool.");
    System.out.println();
    System.out.println("<command>      : [options]");
    System.out.println("  class        : <dir> <target-class> [-v]");
    System.out.println("    Search classes in jar files.");
    System.out.println("    <dir> is start directory for finding jar files.");
    System.out.println("    <target-class> is regex expression of class name");
    System.out.println("    which you are looking for. If not specified, all");
    System.out.println("    classes are shown.");
    System.out.println("    [-v] is flag to enable verbose mode. Default is");
    System.out.println("    disabled.");
    System.out.println("  constpool    : <dir> <target-str> [-v]");
    System.out.println("    Search constant pool entries (UTF8 info) in jar files.");
    System.out.println("    <dir> is start directory for finding jar files.");
    System.out.println("    <target-str> is regex expression of constant pool");
    System.out.println("    entry (UTF8 info) which you are looking for.");
    System.out.println("    [-v] is flag to enable verbose mode. Default is");
    System.out.println("    disabled.");
    System.out.println("  constpool-all : <dir>");
    System.out.println("    Show all constant pool entry (UTF8 info).");
    System.out.println("    <dir> is start directory for finding jar files.");
    System.out.println();
    
    System.exit(-1);
  }
}
