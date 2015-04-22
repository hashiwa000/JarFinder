package jp.hashiwa.jarfinder;

/**
 * jarファイルに対して処理を行う機能。
 *
 * 最初にdoStart()を呼び出す。
 * その後、一つのjarファイルに対してdoOneJar()を一度呼び出す処理を
 * 全てのjarファイルに対して行う。
 * 最後にdoEnd()を呼び出す。
 *
 * @author Hashiwa
 */
public interface JarFileProcessor {
  /**
   * 個々のjarファイルに対する処理。
   * @param jarPath jarファイルのパス
   */
  void doOneJar(String jarPath);

  /**
   * 全てのjarファイルに対する処理の前に行われる処理。
   */
  void doStart();

  /**
   * 全てのjarファイルに対する処理の後に行われる処理。
   */
  void doEnd();
}
