package ch.kerbtier.amarillo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClassWrapper {

  private String pattern;
  private Verb httpMethod = Verb.GET;
  private List<MethodWrapper> callMatchers = new ArrayList<>();
  
  public ClassWrapper(Class<?> subject) {
    
    Route annotation = subject.getAnnotation(Route.class);
    if(annotation != null) {
      if(!annotation.pattern().equals(Route.NULL)) {
        pattern = annotation.pattern();
        if(annotation.verb() != Verb.UNDEFINED) {
          httpMethod = annotation.verb();
        }
      }
    }

    for(Method method: subject.getMethods()) {
      Route action = method.getAnnotation(Route.class);
      if(action != null) {
        MethodWrapper cm = new MethodWrapper(pattern, httpMethod, subject, method, action);
        callMatchers.add(cm);
      }
    }
    
  }
  
  public List<Call> getCall(String path, Verb method) {
    List<Call> calls = null;
    for(MethodWrapper cm: callMatchers) {
      Call call = cm.getCall(path, method);
      if(call != null) {
        if(calls == null) {
          calls = new ArrayList<>();
        }
        calls.add(call);
      }
    }

    if(calls != null) {
      return calls;
    }
    return Collections.emptyList();
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for(MethodWrapper cm: callMatchers) {
      sb.append(cm.getPattern() + ":" + cm.getVerb() + "\n");
    }
    return sb.toString();
  }
}
