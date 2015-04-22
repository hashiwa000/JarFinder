package jp.hashiwa.jarfinder;

/**
 * jar�t�@�C���ɑ΂��ď������s���@�\�B
 *
 * �ŏ���doStart()���Ăяo���B
 * ���̌�A���jar�t�@�C���ɑ΂���doOneJar()����x�Ăяo��������
 * �S�Ă�jar�t�@�C���ɑ΂��čs���B
 * �Ō��doEnd()���Ăяo���B
 *
 * @author Hashiwa
 */
public interface JarFileProcessor {
  /**
   * �X��jar�t�@�C���ɑ΂��鏈���B
   * @param jarPath jar�t�@�C���̃p�X
   */
  void doOneJar(String jarPath);

  /**
   * �S�Ă�jar�t�@�C���ɑ΂��鏈���̑O�ɍs���鏈���B
   */
  void doStart();

  /**
   * �S�Ă�jar�t�@�C���ɑ΂��鏈���̌�ɍs���鏈���B
   */
  void doEnd();
}
