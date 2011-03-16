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
package info.piwai.buildergen.helper;

import info.piwai.buildergen.api.Build;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * A helper that centralize some code to deal with constructors
 * 
 * @author Pierre-Yves Ricau (py.ricau at gmail.com)
 */
public class ElementHelper {

	/**
	 * @return all accessible constructors available for the given
	 * {@link TypeElement}, ie any constructor that is not private.
	 */
	public Set<ExecutableElement> findAccessibleConstructors(TypeElement buildableElement) {
		Set<ExecutableElement> constructors = new HashSet<ExecutableElement>();
		List<? extends Element> enclosedElements = buildableElement.getEnclosedElements();
		for (Element enclosedElement : enclosedElements) {
			ElementKind enclosedElementKind = enclosedElement.getKind();
			if (enclosedElementKind == ElementKind.CONSTRUCTOR) {
				if (!enclosedElement.getModifiers().contains(Modifier.PRIVATE)) {
					constructors.add((ExecutableElement) enclosedElement);
				}
			}
		}
		return constructors;
	}

	/**
	 * @return the constructor that the builder should use
	 * @throws IllegalStateException if there are several candidates
	 */
	public ExecutableElement findBuilderConstructor(Set<ExecutableElement> constructors) {
		Set<ExecutableElement> buildersConstructor = findBuilderConstructors(constructors);

		if (buildersConstructor.size() == 1) {
			return buildersConstructor.iterator().next();
		} else {
			throw new IllegalStateException("No valid constructor found, this element shouldn't have been validated.");
		}

	}

	/**
	 * @return the given set of constructors if its size is one. Otherwise, returns the constructors marked with {@link Build}.
	 */
	public Set<ExecutableElement> findBuilderConstructors(Set<ExecutableElement> constructors) {
		if (constructors.size() == 1) {
			return constructors;
		} else {
			Set<ExecutableElement> buildersConstructors = new HashSet<ExecutableElement>();
			for (ExecutableElement candidateConstructor : constructors) {
				if (candidateConstructor.getAnnotation(Build.class) != null) {
					buildersConstructors.add(candidateConstructor);
				}
			}
			return buildersConstructors;
		}
	}

}
