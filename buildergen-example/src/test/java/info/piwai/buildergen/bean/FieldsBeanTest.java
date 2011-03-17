/*
 * Copyright 2011 Pierre-Yves Ricau (py.ricau at gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package info.piwai.buildergen.bean;

import static org.junit.Assert.assertSame;
import info.piwai.buildergen.bean.FieldsBean;
import info.piwai.buildergen.bean.FieldsBeanBuilder;
import info.piwai.buildergen.bean.SomeObject;

import org.junit.Test;

/**
 * @author Pierre-Yves Ricau (py.ricau at gmail.com)
 */
public class FieldsBeanTest {

	@Test
	public void fieldsAssignment() {

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
	public void fieldsReassignment() {
		Integer integerField1 = 42;
		Integer integerField2 = 69;
		FieldsBean fieldsBean = FieldsBeanBuilder.create() //
				.integerField(integerField1) //
				.integerField(integerField2) //
				.build();

		assertSame(integerField2, fieldsBean.getIntegerField());
	}

}
