package ch.kerbtier.amarillo.tests;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ch.kerbtier.amarillo.Call;
import ch.kerbtier.amarillo.NoMatchException;
import ch.kerbtier.amarillo.Router;
import ch.kerbtier.amarillo.Verb;
import ch.kerbtier.amarillo.tests.actions.MethodParameter;

public class MethodParameterTests {
  private Router routing;
  
  @BeforeMethod
  public void setUp() {
    routing = new Router();
    routing.register(MethodParameter.class);
  }
  
  @Test
  public void testSingleInt() throws NoSuchMethodException, SecurityException {
    Call call = routing.getCall("integer/100", Verb.GET);
    assertEquals(call.getMethod(), MethodParameter.class.getMethod("integer", Integer.TYPE));
    Integer i1 = (Integer)call.execute();
    assertEquals((int)i1, 100);
  }

  @Test(expectedExceptions = NoMatchException.class)
  public void testSingleIntToLow() throws NoSuchMethodException, SecurityException {
    routing.getCall("integer/9", Verb.GET);
  }

  @Test(expectedExceptions = NoMatchException.class)
  public void testSingleIntToHigh() throws NoSuchMethodException, SecurityException {
    routing.getCall("integer/10001", Verb.GET);
  }

  @Test
  public void testDoubleInt() throws NoSuchMethodException, SecurityException {
    Call call = routing.getCall("integer/100/300", Verb.GET);
    assertEquals(call.getMethod(), MethodParameter.class.getMethod("integer", Integer.TYPE, Integer.TYPE));
    Integer i2 = (Integer)call.execute();
    assertEquals((int)i2, 300);
  }

  @Test
  public void testLong() throws NoSuchMethodException, SecurityException {
    Call call = routing.getCall("long/100998", Verb.GET);
    assertEquals(call.getMethod(), MethodParameter.class.getMethod("longSchlong", Long.TYPE));
    long l1 = (Long)call.execute();
    assertEquals(l1, 100998);
  }
  

}
