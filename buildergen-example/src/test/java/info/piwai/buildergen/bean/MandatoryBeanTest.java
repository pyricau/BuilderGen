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

import org.junit.Test;

/**
 * @author Pierre-Yves Ricau (py.ricau at gmail.com)
 */
public class MandatoryBeanTest {
	
	@Test
	public void mandatoryParametersAssignmentThroughtCreate() {
		String stringField = "42";
		SomeObject someObjectField = new SomeObject();

		MandatoryBean bean = MandatoryBeanBuilder.create(stringField, someObjectField).build();
		
		assertSame(stringField, bean.getStringField());
		assertSame(someObjectField, bean.getSomeObjectField());
	}
	
	@Test
	public void mandatoryParametersAssignmentThroughtConstructor() {
		String stringField = "42";
		SomeObject someObjectField = new SomeObject();

		MandatoryBean bean = new MandatoryBeanBuilder(stringField, someObjectField).build();
		
		assertSame(stringField, bean.getStringField());
		assertSame(someObjectField, bean.getSomeObjectField());
	}
	
	@Test
	public void mandatoryParametersReassignment() {
		String stringField1 = "42";
		String stringField2 = "69";
		SomeObject someObjectField = new SomeObject();

		MandatoryBeanBuilder builder = MandatoryBeanBuilder.create(stringField1, someObjectField);
		
		builder.stringField(stringField2);
		
		MandatoryBean bean = builder.build();
		
		assertSame(stringField2, bean.getStringField());
	}


}
