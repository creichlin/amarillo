package ch.kerbtier.amarillo.tests.actions;

import ch.kerbtier.amarillo.Route;

@SuppressWarnings("unused")
public class MethodParameter {

  @Route(pattern = "integer/(\\d{2,4})")
  public Integer integer(int integer) {
    return integer;
  }
  
  @Route(pattern = "integer/(\\d{2,4})/(\\d{2,4})")
  public Integer integer(int int1, int int2) {
    return int2;
  }
  
  @Route(pattern = "long/(\\d+)")
  public Long longSchlong(long l1) {
    return l1;
  }
  
  @Route(pattern = "enum/(\\w+)")
  public TestEnum enumTest(TestEnum e) {
    return e;
  }
  
}
