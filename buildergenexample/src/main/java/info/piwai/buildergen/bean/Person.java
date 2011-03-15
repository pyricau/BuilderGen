package info.piwai.buildergen.bean;

import info.piwai.buildergen.api.Buildable;

@Buildable
public class Person {

    private final int age;
    private final String name;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

	public int getAge() {
		return age;
	}

	public String getName() {
		return name;
	}
    
}
