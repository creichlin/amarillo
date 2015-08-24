package ch.kerbtier.amarillo.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
import ch.kerbtier.amarillo.Call;
import ch.kerbtier.amarillo.Router;
import ch.kerbtier.amarillo.Verb;
import ch.kerbtier.amarillo.tests.actions.MethodPattern;

public class MethodPatternTests {
  
  private Router routing;
  
  @BeforeMethod
  public void setUp() {
    routing = new Router();
    
    routing.register(MethodPattern.class);
  }
  
  
  @Test
  public void rootGetTest() throws NoSuchMethodException, SecurityException {
    Call call = routing.find("", Verb.GET);
        
    assertEquals(call.getMethod(), MethodPattern.class.getMethod("rootGet"));
  }

  @Test
  public void rootPostTest() throws NoSuchMethodException, SecurityException {
    Call call = routing.find("", Verb.POST);
        
    assertEquals(call.getMethod(), MethodPattern.class.getMethod("rootPost"));
  }

  @Test
  public void pathGetTest() throws NoSuchMethodException, SecurityException {
    Call call = routing.find("path", Verb.GET);
        
    assertEquals(call.getMethod(), MethodPattern.class.getMethod("pathGet"));
  }

  @Test
  public void pathPostTest() throws NoSuchMethodException, SecurityException {
    Call call = routing.find("path", Verb.POST);
        
    assertEquals(call.getMethod(), MethodPattern.class.getMethod("pathPost"));
  }
}
