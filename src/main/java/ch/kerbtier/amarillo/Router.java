package ch.kerbtier.amarillo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Router {
  private List<ClassWrapper> classWrappers = new ArrayList<>();

  public void register(Class<? extends Object> def) {
    ClassWrapper classWrapper = new ClassWrapper(def);
    classWrappers.add(classWrapper);
  }

  public Call find(String path, Verb verb) {
    List<Call> calls = findAll(path, verb);
    if (calls.size() > 0) {
      return calls.get(0);
    }
    throw new NoMatchException("path " + path + " with verb " + verb + " has no match");
  }

  /**
   * gets all matched calls, with highest specificity first
   * @param path
   * @param method
   * @return
   */
  public List<Call> findAll(String path, Verb method) {
    List<Call> calls = new ArrayList<>();

    for (ClassWrapper dm : classWrappers) {
      calls.addAll(dm.getCall(path, method));
    }

    Collections.sort(calls, new Comparator<Call>() {

      @Override
      public int compare(Call o1, Call o2) {
        return Integer.compare(o1.getTotalMatched(), o2.getTotalMatched());
      }
    });

    return calls;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (ClassWrapper dm : classWrappers) {
      sb.append(dm.toString());
    }
    return sb.toString();
  }
}
