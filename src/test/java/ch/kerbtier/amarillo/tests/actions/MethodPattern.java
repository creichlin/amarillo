package ch.kerbtier.amarillo.tests.actions;

import ch.kerbtier.amarillo.Route;
import ch.kerbtier.amarillo.Verb;


public class MethodPattern {

  @Route
  public void rootGet() {
    // unused
  }
  
  @Route(verb = Verb.POST)
  public void rootPost() {
    // unused
  }

  @Route(pattern = "path")
  public void pathGet() {
    // unused
  }
  
  @Route(pattern = "path", verb = Verb.POST)
  public void pathPost() {
    // unused
  }
  
}
