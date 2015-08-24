package ch.kerbtier.amarillo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Router {
  private List<ClassWrapper> classWrappers = new ArrayList<>();

  public void register(Class<? extends Object> def) {
    ClassWrapper dispatcher = new ClassWrapper(def);
    classWrappers.add(dispatcher);
  }

  public Call getCall(String path, Verb verb) {
    List<Call> calls = getCalls(path, verb);
    if (calls.size() > 0) {
      return calls.get(0);
    }
    throw new NoMatchException("path " + path + " with verb " + verb + " has no match");
  }

  public List<Call> getCalls(String path, Verb method) {
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
