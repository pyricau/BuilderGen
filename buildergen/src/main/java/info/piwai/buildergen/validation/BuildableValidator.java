package info.piwai.buildergen.validation;

import info.piwai.buildergen.api.Build;
import info.piwai.buildergen.api.Buildable;
import info.piwai.buildergen.helper.ElementHelper;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

public class BuildableValidator {

	private final Messager messager;
	private final ElementHelper elementHelper;

	public BuildableValidator(Messager messager, ElementHelper elementHelper) {
		this.messager = messager;
		this.elementHelper = elementHelper;
	}

	public boolean validate(TypeElement element) {

		IsValid valid = new IsValid();

		if (element.getKind() != ElementKind.CLASS) {
			valid.invalidate();
			printBuildableError(element, "%s can only be used on class");
		}
		
		if (element.getEnclosingElement().getKind()!=ElementKind.PACKAGE) {
			valid.invalidate();
			printBuildableError(element, "%s can only be used on a top-level class, no inner class please");
		}

		if (element.getModifiers().contains(Modifier.ABSTRACT)) {
			valid.invalidate();
			printBuildableError(element, "%s cannot be used on an abstract class");
		}

		Set<ExecutableElement> constructors = elementHelper.findAccessibleConstructors(element);

		if (constructors.size() == 0) {
			valid.invalidate();
			printBuildableError(element, "No accessible constructor found. Please provide at least one not private constructor.");
		} else if (constructors.size() > 1) {
			Set<ExecutableElement> builderConstructors = elementHelper.findBuilderConstructors(constructors);
			if (builderConstructors.size() == 0) {
				valid.invalidate();
				String message = "Multiple constructor candidates found. Please use @"+Build.class.getSimpleName()+" annotation on the selected constructor.";
				printBuildableError(element, message);
				for (ExecutableElement constructor : constructors) {
					messager.printMessage(Diagnostic.Kind.ERROR, message, constructor);
				}
			} else if (builderConstructors.size() > 1) {
				valid.invalidate();
				for (ExecutableElement constructor : builderConstructors) {
					printBuildError(constructor, "%s should not be used more than once in a class.");
				}
			}
		}
		return valid.isValid();
	}

	/**
	 * @param message
	 *            if the string contains a %s, it will be replaced with the
	 *            annotation name
	 */
	private void printBuildError(Element annotatedElement, String message) {
		printMessageOnAnnotation(Diagnostic.Kind.ERROR, annotatedElement, Build.class, String.format(message, "@" + Build.class.getSimpleName()));
	}

	/**
	 * @param message
	 *            if the string contains a %s, it will be replaced with the
	 *            annotation name
	 */
	private void printBuildableError(Element annotatedElement, String message) {
		printMessageOnAnnotation(Diagnostic.Kind.ERROR, annotatedElement, Buildable.class, String.format(message, "@" + Buildable.class.getSimpleName()));
	}

	private void printMessageOnAnnotation(Diagnostic.Kind diagnosticKind, Element annotatedElement, Class<? extends Annotation> annotationClass, String message) {
		AnnotationMirror annotationMirror = findAnnotationMirror(annotatedElement, annotationClass);
		if (annotationMirror != null) {
			messager.printMessage(diagnosticKind, message, annotatedElement, annotationMirror);
		} else {
			messager.printMessage(diagnosticKind, message, annotatedElement);
		}
	}

	private AnnotationMirror findAnnotationMirror(Element annotatedElement, Class<? extends Annotation> annotationClass) {
		List<? extends AnnotationMirror> annotationMirrors = annotatedElement.getAnnotationMirrors();

		for (AnnotationMirror annotationMirror : annotationMirrors) {
			TypeElement annotationElement = (TypeElement) annotationMirror.getAnnotationType().asElement();
			if (hasSameQualifiedName(annotationElement, annotationClass)) {
				return annotationMirror;
			}
		}
		return null;
	}

	private boolean hasSameQualifiedName(TypeElement annotation, Class<? extends Annotation> annotationClass) {
		return annotation.getQualifiedName().toString().equals(annotationClass.getName());
	}

}
