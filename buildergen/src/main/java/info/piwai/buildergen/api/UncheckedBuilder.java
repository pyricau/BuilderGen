/*
 * Copyright 2010-2011 Pierre-Yves Ricau (py.ricau at gmail.com)
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
package info.piwai.buildergen.api;

/**
 * A {@link Builder} that does not throw Checked exceptions when its
 * {@link Build} method is called. The builders generated with {@link Buildable}
 * implement this interface, unless the {@link Buildable} annotated class has a
 * constructor that declares throwing checked exceptions.
 * 
 * @author Pierre-Yves Ricau (py.ricau at gmail.com)
 */
public interface UncheckedBuilder<T> extends Builder<T> {
	/**
	 * @return a new T instance, each time the {@link #build()} method is
	 *         called.
	 */
	T build();
}
