package ch.kerbtier.amarillo.tests.actions;

import ch.kerbtier.amarillo.Route;
import ch.kerbtier.amarillo.Verb;


public class MethodPattern {

  @Route
  public void rootGet() {
    
  }
  
  @Route(verb = Verb.POST)
  public void rootPost() {
    
  }

  @Route(pattern = "path")
  public void pathGet() {
    
  }
  
  @Route(pattern = "path", verb = Verb.POST)
  public void pathPost() {
    
  }
  
}
