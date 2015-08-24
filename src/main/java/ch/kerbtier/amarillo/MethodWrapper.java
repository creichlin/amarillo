package ch.kerbtier.amarillo;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class MethodWrapper {

  private Class<?> subject;
  private Method method;
  private Pattern pattern;
  private Verb verb;

  // private HTTPMethod httpMethod;

  protected MethodWrapper(String parentPattern, Verb verb, Class<?> subject, Method method, Route action) {
    this.subject = subject;
    this.method = method;
    // this.httpMethod = action.method();

    String fixedPattern = "^";
    
    String actionPattern = null;
    if (!action.pattern().equals(Route.NULL)) {
      actionPattern = action.pattern();
    }

    
    if (parentPattern == null) {
      if(actionPattern == null) {
        // will be empty path
      } else { // actionPattern != null
        fixedPattern += actionPattern;
      }
    } else { // parentPattern != null
      if(actionPattern == null) {
        fixedPattern += parentPattern;
      } else { // actionPattern != null
        fixedPattern += parentPattern + "/" + actionPattern;
      }
    }

    fixedPattern += "$";

    if (action.verb() != Verb.UNDEFINED) {
      this.verb = action.verb();
    } else {
      this.verb = verb;
    }

    try {
      pattern = Pattern.compile(fixedPattern);
    } catch (PatternSyntaxException e) {
      throw new RuntimeException("method " + method + " has invalid pattern", e);
    }
  }

  protected Call getCall(String path, Verb httpMethod) {
    if (this.verb == httpMethod) {
      Matcher matcher = pattern.matcher(path);
      if (matcher.find()) {
        return new Call(subject, method, matcher);
      }
    }
    return null;
  }

  protected Verb getVerb() {
    return verb;
  }

  protected Pattern getPattern() {
    return pattern;
  }
  
}

