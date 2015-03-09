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
    default:
      usage();
    }
  }
  private void parseClassArgs(String[] args, PrintWriter out) {
    if (3 < args.length) usage();

    String className = null;
    if (args.length == 3) {
      className = args[2];
    }
    
    this.proc = new ClassNameFilterProcessor(className, out);
  }
  
  private void parseConstantPoolArgs(String[] args, PrintWriter out) {
    if (3 < args.length) usage();

    String className = null;
    if (args.length == 3) {
      className = args[2];
    }
    
    this.proc = new ConstantPoolFilterProcessor(className, out);
  }
  
  private static void usage() {
    System.out.println("java -jar xxx.jar <command> <dir> [options]");
    System.out.println();
    System.out.println("<command>");
    System.out.println("  class : find class from jar files.");
    System.out.println("  constpool : find string in all constant pool.");
    System.out.println();
    System.out.println("<dir> : start directory for finding jar files");
    System.out.println();
    System.out.println(" <comman>class : [options]<target-class>");
    System.out.println("    target-class : name of class which you are");
    System.out.println("    looking for. If not specified, all classes are");
    System.out.println("    shown.");
    System.out.println(" <comman>constpool : [options]<target-str>");
    System.out.println("    target-str : string which you are looking for");
    System.out.println("    If not specified, all constant pool utf8 strings");
    System.out.println("    are shown.");
    System.out.println();
    
    System.exit(-1);
  }
}
