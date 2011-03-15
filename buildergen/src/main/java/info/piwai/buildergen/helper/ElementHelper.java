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

public class ElementHelper {
	
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
	
	public ExecutableElement findBuilderConstructor(Set<ExecutableElement> constructors) {
		Set<ExecutableElement> buildersConstructor = findBuilderConstructors(constructors);
		
		if (buildersConstructor.size()==1) {
			return buildersConstructor.iterator().next();
		} else {
			throw new IllegalStateException("No valid constructor found, this element shouldn't have been validated.");
		}
		
	}
	
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
