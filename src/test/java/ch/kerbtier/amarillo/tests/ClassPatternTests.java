package ch.kerbtier.amarillo.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
import ch.kerbtier.amarillo.Call;
import ch.kerbtier.amarillo.Router;
import ch.kerbtier.amarillo.Verb;
import ch.kerbtier.amarillo.tests.actions.ClassPattern;

public class ClassPatternTests {
  
  private Router routing;
  
  @BeforeMethod
  public void setUp() {
    routing = new Router();
    
    routing.register(ClassPattern.class);
  }
  
  
  @Test
  public void rootGetTest() throws NoSuchMethodException, SecurityException {
    Call call = routing.find("class", Verb.GET);
        
    assertEquals(call.getMethod(), ClassPattern.class.getMethod("rootGet"));
  }

  @Test
  public void rootPostTest() throws NoSuchMethodException, SecurityException {
    Call call = routing.find("class", Verb.POST);
        
    assertEquals(call.getMethod(), ClassPattern.class.getMethod("rootPost"));
  }
}
