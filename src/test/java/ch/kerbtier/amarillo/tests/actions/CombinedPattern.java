package ch.kerbtier.amarillo.tests.actions;

import ch.kerbtier.amarillo.Route;
import ch.kerbtier.amarillo.Verb;


@Route(pattern = "class")
public class CombinedPattern {

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
