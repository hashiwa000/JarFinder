package jp.hashiwa.jarfinder;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.stream.Stream;

/**
 * Main�N���X�B
 * @author Hashiwa
 */
public class JarFinderMain {

  /**
   * Main���\�b�h
   * @param args �A�v���P�[�V�����̈���
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    /**
     * �A�v���P�[�V�����̏o�͐�B�p�t�H�[�}���X���P�̂��߃o�b�t�@�����O����B
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
