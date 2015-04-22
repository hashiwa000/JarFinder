package jp.hashiwa.jarfinder;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.stream.Stream;

/**
 * Mainクラス。
 * @author Hashiwa
 */
public class JarFinderMain {

  /**
   * Mainメソッド
   * @param args アプリケーションの引数
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    /**
     * アプリケーションの出力先。パフォーマンス改善のためバッファリングする。
     */
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
