package jp.hashiwa.jarfinder.impl;

import com.sun.istack.internal.NotNull;
import javafx.scene.effect.SepiaTone;

import java.util.Set;

/**
 * Created by Hashiwa on 2015/05/28.
 */
public class ExtendsUpwardManager extends ExtendsManager {
  @Override
  public void addSuperClass(@NotNull String subClass, @NotNull String superClass) {
    super.addExtendsValue(subClass, superClass);
  }

  @Override
  public void addInterface(@NotNull String cls, @NotNull String itf) {
    super.addImplementsValue(cls, itf);
  }

  public String getSuperClass(@NotNull String subClass) {
    Set<String> superClasses = getExtendsValue(subClass);
    if (1 < superClasses.size())
      throw new Error("Fatal Error: the number of super classes of " +
              subClass + " is " + superClasses.size());
    return superClasses.iterator().next();
  }

  public Set<String> getInterfaces(String cls) {
    return getImplementsValue(cls);
  }
}
