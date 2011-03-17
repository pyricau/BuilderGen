package info.piwai.buildergen.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import info.piwai.buildergen.api.Buildable;
import info.piwai.buildergen.helper.ElementHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.tools.Diagnostic.Kind;

import org.junit.Before;
import org.junit.Test;

public class BuildableValidatorTest {

	private ProcessingEnvironment processingEnv;
	private ElementHelper elementHelper;
	private BuildableValidator buildableValidator;
	private TypeElement element;
	private Element enclosingElement;
	private ExecutableElement constructor;
	private Iterator<ExecutableElement> iterator;
	private Buildable buildableAnnotation;
	private Messager messager;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Before
	public void setup() {

		processingEnv = mock(ProcessingEnvironment.class);
		
		messager = mock(Messager.class);
		
		when(processingEnv.getMessager()).thenReturn(messager);
		
		elementHelper = mock(ElementHelper.class);

		constructor = mock(ExecutableElement.class);
		when(constructor.getThrownTypes()).thenReturn(new ArrayList());
		
		Set<ExecutableElement> constructors = mock(Set.class, RETURNS_DEEP_STUBS);
		when(constructors.size()).thenReturn(1);
		
		iterator = mock(Iterator.class);
		when(iterator.next()).thenReturn(constructor);
		when(constructors.iterator()).thenReturn(iterator);
		when(elementHelper.findAccessibleConstructors(any(TypeElement.class))).thenReturn(constructors);

		buildableValidator = new BuildableValidator(processingEnv, elementHelper);

		createMockValidTypeElement();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void createMockValidTypeElement() {
		element = mock(TypeElement.class);

		when(element.getKind()).thenReturn(ElementKind.CLASS);

		enclosingElement = mock(Element.class);
		when(element.getEnclosingElement()).thenReturn(enclosingElement);

		when(enclosingElement.getKind()).thenReturn(ElementKind.PACKAGE);

		Set<Modifier> modifiers = mock(Set.class);
		when(enclosingElement.getModifiers()).thenReturn(modifiers);

		when(modifiers.contains(Modifier.ABSTRACT)).thenReturn(true);
		
		buildableAnnotation = mock(Buildable.class);
		when(buildableAnnotation.value()).thenReturn("Builder");
		when(element.getAnnotation(Buildable.class)).thenReturn(buildableAnnotation);
		
		AnnotationMirror annotationMirror = mock(AnnotationMirror.class);
		
		ArrayList annotationMirrors = new ArrayList();
		annotationMirrors.add(annotationMirror);
		when(element.getAnnotationMirrors()).thenReturn(annotationMirrors);
		
		DeclaredType annotationMirrorType = mock(DeclaredType.class);
		when(annotationMirror.getAnnotationType()).thenReturn(annotationMirrorType);
		
		TypeElement annotationMirrorElement = mock(TypeElement.class);
		when(annotationMirrorType.asElement()).thenReturn(annotationMirrorElement);
		
		Name annotationMirrorName = mock(Name.class);
		when(annotationMirrorElement.getQualifiedName()).thenReturn(annotationMirrorName);
		
		when(annotationMirrorName.toString()).thenReturn(Buildable.class.getName());
		
		Map annotationMirrorElementValues = new HashMap();
		AnnotationValue annotationValue = mock(AnnotationValue.class);
		annotationMirrorElementValues.put(mock(ExecutableElement.class), annotationValue);
		
		
		when(annotationMirror.getElementValues()).thenReturn(annotationMirrorElementValues);
	}

	@Test
	public void validTypeElementIsValid() {
		boolean valid = buildableValidator.validate(element);
		verify(messager, never()).printMessage(any(Kind.class), anyString());
		verify(messager, never()).printMessage(any(Kind.class), anyString(), any(Element.class));
		verify(messager, never()).printMessage(any(Kind.class), anyString(), any(Element.class), any(AnnotationMirror.class));
		verify(messager, never()).printMessage(any(Kind.class), anyString(), any(Element.class), any(AnnotationMirror.class), any(AnnotationValue.class));
		assertTrue(valid);
	}

	@Test
	public void emptyCustomBuilderNameIsNotValid() {
		when(buildableAnnotation.value()).thenReturn("");
		
		boolean valid = buildableValidator.validate(element);
		
		verify(messager).printMessage(any(Kind.class), anyString(), any(Element.class), any(AnnotationMirror.class), any(AnnotationValue.class));
		
		assertFalse(valid);
	}


}
