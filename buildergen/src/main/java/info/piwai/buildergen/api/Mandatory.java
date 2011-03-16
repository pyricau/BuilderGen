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
package info.piwai.buildergen.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use on a constructor parameter in a {@link Buildable} annotated class.<br />
 * <br />
 * This annotation sets the parameter as mandatory for the generated
 * {@link Builder}. The parameter will therefore have to be provided to the
 * builder when it is instantiated, either through its constructor or through
 * its static create() method.<br />
 * <br />
 * Please use wisely: too much @{@link Mandatory} parameters would ruin your {@link Builder}'s usability.
 * 
 * @author Pierre-Yves Ricau (py.ricau at gmail.com)
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.PARAMETER)
public @interface Mandatory {
}
