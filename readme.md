amarillo
========

Java path routing implementation.

Why?
----

It's fun and small.

What does it do?
----------------

Methods can be annotated with a Route pattern. Those methods can be found by matching them against a path.

The method that matches the path best can be executed. Parameters in the pattern will be used as the methods parameters.

This is commonly used for matching urls in a browser against code on the server that should be executed. But it can be used for other
scenarios too.


How?
----

To use routing one has to create a Router instance and register classes which contain methods with the Route annotation.

    Router router = new Router();
    router.register(TestRoutes.class);

A route class might look like:

    @Route(pattern = "entity/(\\d+)")
    class TestRoutes {
      @Route()
      public void viewEntity(int id) {...

      @Route(pattern = "edit")
      public void editEntity(int id) {...

      @Route(verb = Verb.POST)
      public void saveEntity(int id) {...
    }

Those methods can be called like:

    Call call = router.find("entity/30/edit", Verb.GET);
    call.execute();

This can be called from a servlet which uses url path and HTTp method or it can also be used in other ways.
For example in an event bus or for command line input or for accessing a database.
