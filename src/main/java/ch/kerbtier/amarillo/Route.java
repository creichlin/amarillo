package ch.kerbtier.amarillo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Route {
  String pattern() default NULL;
  Verb verb() default Verb.UNDEFINED;
  public static final String NULL = "NULL_oZShvXLv0RMTBhe61cN7";
}
