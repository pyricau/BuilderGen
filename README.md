# BuilderGen 0.2

## Intro
BuilderGen uses [APT](http://download.oracle.com/javase/6/docs/technotes/guides/apt/index.html) and [CodeModel](http://codemodel.java.net/) to generate builders at compile time. The builders implement Josh Bloch's Builder Pattern from [Effective Java](http://java.sun.com/docs/books/effective/).

If you wonder how it actually works, read the dedicated [wiki page](https://github.com/pyricau/BuilderGen/wiki/How it works).

## 3 simple steps

* Configure your project to use BuilderGen annotation processing. Instructions [in the wiki](https://github.com/pyricau/BuilderGen/wiki).
* Add ```@Buildable``` to any class you wish.
* A builder is automatically created for this class.

## Code example

Suppose you have a Person class:

```java
@Buildable
public class Person {

    private final int age;
    private final String name;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    // [...]
}
```

BuilderGen automatically generates a PersonBuilder, and you can now do the following: 

    PersonBuilder builder = PersonBuilder.create();
    Person john42 = builder.name("John Smith").age(42).build();
    Person john69 = builder.age(69).build();
    
[Get started](https://github.com/pyricau/BuilderGen/wiki) with BuilderGen!
    
## Generated code

Have a look at the nicely generated PersonBuilder code :

```java
//
// WARNING: do not edit this file, it has been generated using BuilderGen: https://github.com/pyricau/BuilderGen.
//


package info.piwai.buildergen.bean;

import javax.annotation.Generated;
import info.piwai.buildergen.api.Builder;


/**
 * Builder for the {@link Person } class.<br />
 * <br />
 * This builder implements Joshua Bloch's builder pattern, to let you create {@link Person } instances 
 * without having to deal with complex constructor parameters.
 * It has a fluid interface, which mean you can chain calls to its methods.<br />
 * <br />
 * You can create a new {@link PersonBuilder } instance by calling the {@link #create()} static method, 
 * or its constructor directly.<br />
 * <br />
 * When done with settings fields, you can create {@link Person } instances 
 * by calling the {@link #build()} method.
 * Each call will return a new instance.<br />
 * <br />
 * You can call setters multiple times, and use this builder as an object template.
 * 
 */
@Generated(comments = "Generated by BuilderGen", value = "info.piwai.buildergen.processing.BuilderGenProcessor", date = "2011-03-15T21:02:47.057+0100")
public class PersonBuilder
    implements Builder<Person>
{

    private String name;
    private int age;

    /**
     * Setter for the name parameter.
     * 
     * @param name
     *     the value for the name constructor parameter of the {@link Person } class.
	     * @return
	     *     this, ie the {@link PersonBuilder } instance, to enable chained calls.
	     */
	    public PersonBuilder name(String name) {
	        this.name = name;
	        return this;
	    }
	
	    /**
	     * Setter for the age parameter.
	     * 
	     * @param age
	     *     the value for the age constructor parameter of the {@link Person } class.
	     * @return
	     *     this, ie the {@link PersonBuilder } instance, to enable chained calls.
	     */
	    public PersonBuilder age(int age) {
	        this.age = age;
	        return this;
	    }
	
	    /**
	     * Creates {@link Person } instances based on this builder fields.<br />
	     * <br />
	     * The builder keeps its state after this method has been called.
	     * 
	     * @return
	     *     a new {@link Person } instance.
	     */
	    public Person build() {
	        return new Person(name, age);
	    }
	
	    /**
	     * Static factory method for {@link PersonBuilder } instances.
	     * 
	     * @return
	     *     a new {@link PersonBuilder } instance
	     */
	    public static PersonBuilder create() {
	        return new PersonBuilder();
	    }
	
	}
```
