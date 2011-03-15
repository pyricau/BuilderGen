# BuilderGen

## Intro
BuilderGen uses APT and CoreModel to generate builders (following the Builder Pattern) at compile time.

## Steps

* Configure your project to use BuilderGen annotation processing (no doc yet, please look at the example)
* Add @Buildable to any class you wish.
* A builder is automatically created.

## Code example

Suppose you have a MyBean class :

	@Buildable
	public class MyBean {
		
		private final int intField;
		private final String stringField;
		
		MyBean(int intField, String stringField) {
			this.intField = intField;
			this.stringField = stringField;
		}
	// [...]
	}


You can do the following : 

    MyBean bean = MyBeanBuilder.create().intField(42).stringField("Hello World!").build();
