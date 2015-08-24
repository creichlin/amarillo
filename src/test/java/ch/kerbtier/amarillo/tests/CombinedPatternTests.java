package ch.kerbtier.amarillo.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
import ch.kerbtier.amarillo.Call;
import ch.kerbtier.amarillo.Router;
import ch.kerbtier.amarillo.Verb;
import ch.kerbtier.amarillo.tests.actions.CombinedPattern;

public class CombinedPatternTests {
  
  private Router routing;
  
  @BeforeMethod
  public void setUp() {
    routing = new Router();
    routing.register(CombinedPattern.class);
  }
  
  @Test
  public void rootGetTest() throws NoSuchMethodException, SecurityException {
    Call call = routing.find("class", Verb.GET);
    assertEquals(call.getMethod(), CombinedPattern.class.getMethod("rootGet"));
  }

  @Test
  public void rootPostTest() throws NoSuchMethodException, SecurityException {
    Call call = routing.find("class", Verb.POST);
    assertEquals(call.getMethod(), CombinedPattern.class.getMethod("rootPost"));
  }

  @Test
  public void pathGetTest() throws NoSuchMethodException, SecurityException {
    Call call = routing.find("class/path", Verb.GET);
    assertEquals(call.getMethod(), CombinedPattern.class.getMethod("pathGet"));
  }

  @Test
  public void pathPostTest() throws NoSuchMethodException, SecurityException {
    Call call = routing.find("class/path", Verb.POST);
    assertEquals(call.getMethod(), CombinedPattern.class.getMethod("pathPost"));
  }
}
