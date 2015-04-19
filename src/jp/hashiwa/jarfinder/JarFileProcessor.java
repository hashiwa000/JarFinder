package jp.hashiwa.jarfinder;

public interface JarFileProcessor {
  void doOneJar(String jarPath);
  void doStart();
  void doEnd();
}
