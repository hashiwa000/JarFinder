package jp.hashiwa.jarfinder.impl;

import com.sun.istack.internal.NotNull;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by Hashiwa on 2015/05/28.
 */
public abstract class ExtendsManager {
  private final Map<String, Set<String>> extendsMap = new HashMap<>();
  private final Map<String, Set<String>> implementsMap = new TreeMap<>();

  public abstract void addSuperClass(String subClass, String superClass);
  public abstract void addInterface(String cls, String itf);

  public void addInterfaces(@NotNull String cls, @NotNull String[] interfaces) {
    if (interfaces == null) return;
    Stream.of(interfaces).forEach(
            i -> addInterface(cls, i)
    );
  }

  void addExtendsValue(String key, String value) {
    add(key, value, extendsMap);
  }

  void addImplementsValue(String key, String value) {
    add(key, value, implementsMap);
  }

  Set<String> getExtendsValue(String key) {
    return get(key, extendsMap);
  }

  Set<String> getImplementsValue(String key) {
    return get(key, implementsMap);
  }

  Map<String, Set<String>> getExtendsMap() {
    return extendsMap;
  }

  Map<String, Set<String>> getImplementsMap() {
    return implementsMap;
  }

  private static void add(String key, String value, Map<String, Set<String>> map) {
    Set<String> values = map.get(key);
    if (values == null) {
      values = new TreeSet<>();
      map.put(key, values);
    }
    values.add(value);
  }
  private static Set<String> get(String key, Map<String, Set<String>> map) {
    Set<String> values = map.get(key);
    if (values == null) {
      values = new TreeSet<>();
      map.put(key, values);
    }
    return values;
  }
}
