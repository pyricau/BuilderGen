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
