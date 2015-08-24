package ch.kerbtier.amarillo.tests;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ch.kerbtier.amarillo.Call;
import ch.kerbtier.amarillo.NoMatchException;
import ch.kerbtier.amarillo.Router;
import ch.kerbtier.amarillo.Verb;
import ch.kerbtier.amarillo.tests.actions.MethodParameter;
import ch.kerbtier.amarillo.tests.actions.TestEnum;

public class MethodParameterTests {
  private Router routing;
  
  @BeforeMethod
  public void setUp() {
    routing = new Router();
    routing.register(MethodParameter.class);
  }
  
  @Test
  public void testSingleInt() throws NoSuchMethodException, SecurityException {
    Call call = routing.find("integer/100", Verb.GET);
    assertEquals(call.getMethod(), MethodParameter.class.getMethod("integer", Integer.TYPE));
    Integer i1 = (Integer)call.execute();
    assertEquals((int)i1, 100);
  }

  @Test(expectedExceptions = NoMatchException.class)
  public void testSingleIntToLow() throws NoSuchMethodException, SecurityException {
    routing.find("integer/9", Verb.GET);
  }

  @Test(expectedExceptions = NoMatchException.class)
  public void testSingleIntToHigh() throws NoSuchMethodException, SecurityException {
    routing.find("integer/10001", Verb.GET);
  }

  @Test
  public void testDoubleInt() throws NoSuchMethodException, SecurityException {
    Call call = routing.find("integer/100/300", Verb.GET);
    assertEquals(call.getMethod(), MethodParameter.class.getMethod("integer", Integer.TYPE, Integer.TYPE));
    Integer i2 = (Integer)call.execute();
    assertEquals((int)i2, 300);
  }

  @Test
  public void testLong() throws NoSuchMethodException, SecurityException {
    Call call = routing.find("long/100998", Verb.GET);
    assertEquals(call.getMethod(), MethodParameter.class.getMethod("longSchlong", Long.TYPE));
    long l1 = (Long)call.execute();
    assertEquals(l1, 100998);
  }
  
  @Test
  public void testEnumLower() throws NoSuchMethodException, SecurityException {
    Call call = routing.find("enum/foo", Verb.GET);
    assertEquals(call.getMethod(), MethodParameter.class.getMethod("enumTest", TestEnum.class));
    TestEnum e = (TestEnum)call.execute();
    assertEquals(e, TestEnum.FOO);
  }
  
  @Test
  public void testEnumUpper() throws NoSuchMethodException, SecurityException {
    Call call = routing.find("enum/FOO_BAR", Verb.GET);
    assertEquals(call.getMethod(), MethodParameter.class.getMethod("enumTest", TestEnum.class));
    TestEnum e = (TestEnum)call.execute();
    assertEquals(e, TestEnum.FOO_BAR);
  }
  
  @Test
  public void testEnumCamel() throws NoSuchMethodException, SecurityException {
    Call call = routing.find("enum/fooBar", Verb.GET);
    assertEquals(call.getMethod(), MethodParameter.class.getMethod("enumTest", TestEnum.class));
    TestEnum e = (TestEnum)call.execute();
    assertEquals(e, TestEnum.FOO_BAR);
  }
  

}
