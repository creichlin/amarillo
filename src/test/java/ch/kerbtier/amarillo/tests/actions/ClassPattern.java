package ch.kerbtier.amarillo.tests.actions;

import ch.kerbtier.amarillo.Route;
import ch.kerbtier.amarillo.Verb;


@Route(pattern = "class")
public class ClassPattern {

  @Route
  public void rootGet() {
    // nothing
  }
  
  @Route(verb = Verb.POST)
  public void rootPost() {
    // nothing
  }
  
}
