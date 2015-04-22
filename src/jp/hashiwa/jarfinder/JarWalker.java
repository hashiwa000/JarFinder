package jp.hashiwa.jarfinder;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class JarWalker extends SimpleFileVisitor<Path> {
  private static final boolean ENABLE_ZIP = Boolean.getBoolean("jp.hashiwa.jarfinder.enableZip");

  private final JarFileProcessor processor;
  
  public JarWalker(JarFileProcessor proc) {
    this.processor = proc;
  }
  
  @Override
  public FileVisitResult visitFile(Path path, BasicFileAttributes attr) {
    if (isValidJarFile(path.toFile())) {
      processor.doOneJar(path.toString());
    }
    return FileVisitResult.CONTINUE;
  }
  
  @Override
  public FileVisitResult visitFileFailed(Path path, IOException e) {
    System.err.println("IOException at " + path + " : " + e.getLocalizedMessage());
    return FileVisitResult.CONTINUE;
  }
  
  private boolean isValidJarFile(File jarFile) {
    // jarFile is not found.
    if (!jarFile.exists()) return false;

    // jarFile is not jar file.
    if (jarFile.isDirectory()) return false;
    
    String fileName = jarFile.getName().toLowerCase();
    if (fileName.endsWith(".jar")) {
      // jar file is accepted.
    } else if (fileName.endsWith(".zip")) {
      // zip file is accepted only if ENABLE_ZIP is true
      if (!ENABLE_ZIP) return false;
    } else {
      // other file format is rejected.
      return false;
    }
    
    if (!jarFile.canRead()) {
      System.err.println(jarFile + " cannot be read.");
      return false;
    }
    
    return true;
  }
}
