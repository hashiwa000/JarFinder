package jp.hashiwa.jarfinder;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.stream.Stream;

public class JarFinderMain {
  public static void main(String[] args) throws IOException {
    PrintWriter out = new PrintWriter(
        new BufferedOutputStream(System.out));
    Arguments arg = new Arguments(args, out);

    JarFileProcessor proc = arg.getProcessor();

    proc.doStart();

    JarWalker walker = new JarWalker(proc);
    Stream.of(arg.getDirs()).forEach(
            s -> {
              try {
                Files.walkFileTree(new File(s).toPath(), walker);
              } catch(Exception e) {
                e.printStackTrace();
              }
            }
    );

    proc.doEnd();

    out.flush();
  }
}
