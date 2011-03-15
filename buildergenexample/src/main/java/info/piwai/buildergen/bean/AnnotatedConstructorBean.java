package info.piwai.buildergen.bean;

import info.piwai.buildergen.api.Build;
import info.piwai.buildergen.api.Buildable;

@Buildable
public class AnnotatedConstructorBean {
	
	private String field1;
	private String field2;
	
	public AnnotatedConstructorBean(String field1, String field2) {
		this.field1 = field1;
		this.field2 = field2;
	}
	
	@Build
	public AnnotatedConstructorBean(String field1) {
		this.field1 = field1;
		field2 = "yeah";
	}
	
	
	public AnnotatedConstructorBean() {
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField2() {
		return field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}
}
