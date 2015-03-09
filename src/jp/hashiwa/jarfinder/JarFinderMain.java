package jp.hashiwa.jarfinder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JarFinderMain {
  public static void main(String[] args) throws IOException {
    Arguments arg = new Arguments(args);
    
//    doOneJar("c:\\Program Files (x86)\\NetBeans 8.0.2\\ide\\modules\\org-apache-commons-httpclient.jar");
    Path startPath = new File(arg.getDir()).toPath();
    JarWalker walker = new JarWalker(arg.getProcessor());
    Files.walkFileTree(startPath, walker);
  }
}
