package ch.kerbtier.amarillo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.regex.Matcher;

import com.google.common.base.CaseFormat;

public class Call {

  private Class<?> subject;
  private Method method;
  private Matcher matcher;
  private int totalMatched = 0;

  public Call(Class<?> subject, Method method, Matcher matcher) {
    this.subject = subject;
    this.method = method;
    this.matcher = matcher;

    String totalMatch = "";
    for (int group = 1; group <= matcher.groupCount(); group++) {
      totalMatch += matcher.group(group);
    }
    totalMatched = totalMatch.length();
  }

  public int getTotalMatched() {
    return totalMatched;
  }

  public Method getMethod() {
    return method;
  }

  public boolean has(Class<? extends Annotation> annotation) {
    return method.isAnnotationPresent(annotation);
  }

  public Class<?> getSubject() {
    return subject;
  }

  public Object execute() {
    try {
      return execute(subject.newInstance());
    } catch (Exception e) {
      throw new RuntimeException("could not instantiate subject", e);
    }
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public Object execute(Object instance) {
    Class<?>[] pts = method.getParameterTypes();

    if (pts.length != matcher.groupCount()) {
      throw new RuntimeException("wrong number of parameters for " + method);
    }

    Object[] parameters = new Object[pts.length];

    for (int cnt = 0; cnt < matcher.groupCount(); cnt++) {
      String part = matcher.group(cnt + 1);

      Class<?> parameterType = pts[cnt];

      if (Integer.TYPE.isAssignableFrom(parameterType)) {
        parameters[cnt] = Integer.parseInt(part);
      } else if (Integer.class.isAssignableFrom(parameterType)) {
        if ("null".equals(part)) {
          parameters[cnt] = null;
        } else {
          parameters[cnt] = Integer.parseInt(part);
        }
      } else if (Long.TYPE.isAssignableFrom(parameterType)) {
        parameters[cnt] = Long.parseLong(part);
      } else if (Long.class.isAssignableFrom(parameterType)) {
        if ("null".equals(part)) {
          parameters[cnt] = null;
        } else {
          parameters[cnt] = Long.parseLong(part);
        }
      } else if (String.class.isAssignableFrom(parameterType)) {
        parameters[cnt] = part;
      } else if (parameterType.isEnum()) {
        part = part.replace("-", "_");
        try { // same case enum

          parameters[cnt] = Enum.valueOf(((Class<? extends Enum>) parameterType), part);

        } catch (IllegalArgumentException e) {

          String partHyphen = part;
          // check if mixed case
          if (!part.toLowerCase().equals(part) && !part.toUpperCase().equals(part)) {
            partHyphen = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, part);
          }

          try { // upper case enum
            parameters[cnt] = Enum.valueOf(((Class<? extends Enum>) parameterType), partHyphen.toUpperCase());
          } catch (IllegalArgumentException e2) {
            try { // lower case enum
              parameters[cnt] = Enum.valueOf(((Class<? extends Enum>) parameterType), partHyphen.toLowerCase());
            } catch (IllegalArgumentException e3) {
              String lowerCamel = CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, partHyphen.toLowerCase());
              try { // lowerCamelCaseFrom Hyphen
                parameters[cnt] = Enum.valueOf(((Class<? extends Enum>) parameterType), lowerCamel);
              } catch (IllegalArgumentException e4) {
                throw new IllegalArgumentException("No value found for " + part + " or " + partHyphen.toUpperCase()
                    + " or " + partHyphen.toLowerCase() + " or " + lowerCamel + " in enum "
                    + parameterType.getCanonicalName());
              }
            }
          }
        }

      } else if (Boolean.TYPE.isAssignableFrom(parameterType) || Boolean.class.isAssignableFrom(parameterType)) {
        parameters[cnt] = Boolean.parseBoolean(part);

      } else {
        throw new RuntimeException("invalid parameter type: " + parameters[cnt]);
      }
    }

    try {
      return method.invoke(instance, parameters);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public String toString() {
    return "<" + method.toString() + ">";
  }

}
