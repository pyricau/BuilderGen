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
 * Use on any class to generate its builder. The builder will be located in the
 * same package, and will have the same name with an additional suffix. The
 * default suffix is "Builder". Therefore, a MyBean class annotated with
 * {@link Buildable} will generate a MyBeanBuilder class. The suffix can easily
 * be changed by specifying a different value to the @Builder annotation. For
 * instance :
 * 
 * <pre>
 * <code>
 * @Builder("Factory")
 * public class MyBean {
 * 
 * }</code>
 * </pre>
 * 
 * will generate a MyBeanFactory class.<br />
 * <br />
 * The {@link Buildable} annotated element should be a class. It cannot be an enum, nor an inner class. It should not be abstract.
 * <br />
 * 
 * The {@link Buildable} annotated class should have at least one not private constructor.
 * <br />
 * If the {@link Buildable} annotated class have more than one not private
 * constructor, there should be one (and only one) of those constructors
 * annotated with {@link Build}.<br />
 * <br />
 * The generated builder implements the {@link Builder} interface.<br />
 * <br />
 * The generated builder has a public default constructor, and a create() static
 * factory method as well. This enables chained calls such as : MyBean myBean =
 * MyBeanBuilder.create().name("foo").build();
 * 
 * 
 * @author Pierre-Yves Ricau (py.ricau at gmail.com)
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Buildable {
	String value() default "Builder";
}
