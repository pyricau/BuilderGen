package info.piwai.buildergen.modeling;

import info.piwai.buildergen.api.Build;
import info.piwai.buildergen.api.Buildable;
import info.piwai.buildergen.api.Builder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JInvocation;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;

public class ModelBuilder {
	
	public JCodeModel build(Set<TypeElement> validatedElements) throws JClassAlreadyExistsException {
		JCodeModel codeModel = new JCodeModel();
		for (TypeElement buildableElement : validatedElements) {
			String builderFullyQualifiedName = extractBuilderFullyQualifiedName(buildableElement);

			Set<ExecutableElement> constructors = findAccessibleConstructors(buildableElement);

			ExecutableElement constructor = findBuilderContructor(constructors);
			
			JDefinedClass builderClass = codeModel._class(builderFullyQualifiedName);
			
			JClass buildableClass = codeModel.directClass(buildableElement.getQualifiedName().toString());
			
			JClass builderInterface = codeModel.ref(Builder.class);
			JClass narrowedInterface = builderInterface.narrow(buildableClass);
			
			builderClass._implements(narrowedInterface);
			
			JMethod buildMethod = builderClass.method(JMod.PUBLIC, buildableClass, "build");
			// TODO add constructor exceptions to the method.
			JBlock buildBody = buildMethod.body();
			JInvocation newBuildable = JExpr._new(buildableClass);
			
			List<? extends VariableElement> parameters = constructor.getParameters();
			for(VariableElement parameter : parameters) {
				if (parameter.asType().getKind().isPrimitive()) {
					newBuildable.arg(JExpr.lit(0));
				} else {
					newBuildable.arg(JExpr._null());
				}
			}
			
			buildBody._return(newBuildable);
			
			JMethod createMethod = builderClass.method(JMod.PUBLIC | JMod.STATIC, builderClass, "create");
			createMethod.body()._return(JExpr._new(builderClass));
			
		}
		return codeModel;
	}

	private ExecutableElement findBuilderContructor(Set<ExecutableElement> constructors) {
		if (constructors.size() == 1) {
			return constructors.iterator().next();
		} else {
			for (ExecutableElement candidateConstructor : constructors) {
				if (candidateConstructor.getAnnotation(Build.class) != null) {
					return candidateConstructor;
				}
			}
		}
		throw new IllegalStateException("No valid constructor found, this element shouldn't have been validated.");
	}

	private Set<ExecutableElement> findAccessibleConstructors(TypeElement buildableElement) {
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

	private String extractBuilderFullyQualifiedName(TypeElement buildableElement) {
		Buildable buildableAnnotation = buildableElement.getAnnotation(Buildable.class);
		String builderSuffix = buildableAnnotation.value();

		String buildableFullyQualifiedName = buildableElement.getQualifiedName().toString();

		return  buildableFullyQualifiedName + builderSuffix;
	}

}
