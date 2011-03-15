package info.piwai.buildergen;

import static org.junit.Assert.assertSame;
import info.piwai.buildergen.bean.FieldsBean;
import info.piwai.buildergen.bean.FieldsBeanBuilder;
import info.piwai.buildergen.bean.SomeObject;

import org.junit.Test;

public class FieldsBeanTest {

	@Test
	public void fieldsAssignmentTest() {

		Integer integerField = 42;
		int intField = 69;
		String stringField = "foobar";
		SomeObject someObjectField = new SomeObject();
		FieldsBean fieldsBean = FieldsBeanBuilder.create() //
				.integerField(integerField) //
				.intField(intField) //
				.stringField(stringField) //
				.someObjectField(someObjectField) //
				.build();

		assertSame(integerField, fieldsBean.getIntegerField());
		assertSame(intField, fieldsBean.getIntField());
		assertSame(stringField, fieldsBean.getStringField());
		assertSame(someObjectField, fieldsBean.getSomeObjectField());
	}

	@Test
	public void fieldsReassignmentTest() {
		Integer integerField1 = 42;
		Integer integerField2 = 69;
		FieldsBean fieldsBean = FieldsBeanBuilder.create() //
				.integerField(integerField1) //
				.integerField(integerField2) //
				.build();

		assertSame(integerField2, fieldsBean.getIntegerField());
	}

}
