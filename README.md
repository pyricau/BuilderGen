BuilderGen uses APT and CoreModel to generate builders (following the Builder Pattern) at compile time.

Steps
-----

* Configure your project to use BuilderGen annotation processing (no doc yet, please look at the example)
* Add @Buildable to any class you wish.
* A builder is automatically created.

Suppose you have a MyBean class with a constructor that has two params (param1 and param2).

You can do the following : 

    MyBean bean = MyBeanBuilder.create().param1("hello").param2("world").build();