package info.piwai.buildergen.bean;

import info.piwai.buildergen.api.Buildable;

@Buildable
public class FieldsBean {
	
	private final int intField;
	private final String stringField;
	private final Integer integerField;
	private final SomeObject someObjectField;
	
	FieldsBean(int intField, String stringField, Integer integerField, SomeObject someObjectField) {
		this.intField = intField;
		this.stringField = stringField;
		this.integerField = integerField;
		this.someObjectField = someObjectField;
	}

	public int getIntField() {
		return intField;
	}

	public String getStringField() {
		return stringField;
	}

	public Integer getIntegerField() {
		return integerField;
	}

	public SomeObject getSomeObjectField() {
		return someObjectField;
	}
	
}
