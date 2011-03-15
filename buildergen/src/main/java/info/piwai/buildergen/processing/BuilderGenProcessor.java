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
package info.piwai.buildergen.processing;

import info.piwai.buildergen.api.Buildable;
import info.piwai.buildergen.api.Builder;
import info.piwai.buildergen.generation.SourceGenerator;
import info.piwai.buildergen.modeling.ModelBuilder;
import info.piwai.buildergen.validation.TypeElementValidator;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.Diagnostic.Kind;

import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;

/**
 * Annotation processor that processes {@link Buildable} annotations, validates
 * them and generates the corresponding {@link Builder}s.
 * 
 * @author Pierre-Yves Ricau (py.ricau at gmail.com)
 */
@SupportedAnnotationClasses(Buildable.class)
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class BuilderGenProcessor extends AnnotatedAbstractProcessor {

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		try {
			processThrowing(roundEnv);
		} catch (Exception e) {
			printError(annotations, roundEnv, e);
		}
		return true;
	}

	private void processThrowing(RoundEnvironment roundEnv) throws Exception {
		printCompileNote();

		Set<TypeElement> annotatedElements = getBuildableAnnotatedElements(roundEnv);

		printNumberOfBuildables(annotatedElements);

		Set<TypeElement> validatedElements = validateElements(annotatedElements);

		JCodeModel codeModel = buildModel(validatedElements);

		generateSources(codeModel);
	}

	private void printCompileNote() {
		Messager messager = processingEnv.getMessager();
		messager.printMessage(Diagnostic.Kind.NOTE, "BuilderGen is processing annotations");
	}

	private Set<TypeElement> getBuildableAnnotatedElements(RoundEnvironment roundEnv) {
		@SuppressWarnings("unchecked")
		Set<TypeElement> annotatedElements = (Set<TypeElement>) roundEnv.getElementsAnnotatedWith(Buildable.class);
		return annotatedElements;
	}

	private void printNumberOfBuildables(Set<?> buildableElements) {
		Messager messager = processingEnv.getMessager();
		messager.printMessage(Kind.NOTE, "Found " + buildableElements.size() + " Buildable classes");
	}

	private Set<TypeElement> validateElements(Set<TypeElement> annotatedElements) {
		TypeElementValidator validator = new TypeElementValidator();

		Set<TypeElement> validatedElements = new HashSet<TypeElement>();
		for (TypeElement annotatedElement : annotatedElements) {
			if (validator.validate(annotatedElement)) {
				validatedElements.add(annotatedElement);
			}
		}
		return validatedElements;
	}

	private JCodeModel buildModel(Set<TypeElement> validatedElements) throws JClassAlreadyExistsException {
		ModelBuilder modelBuilder = new ModelBuilder();
		return modelBuilder.build(validatedElements);
	}

	private void generateSources(JCodeModel codeModel) throws IOException {
		Filer filer = processingEnv.getFiler();
		SourceGenerator sourceGenerator = new SourceGenerator(filer);
		sourceGenerator.generate(codeModel);
	}

	private void printError(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv, Exception e) {
		/*
		 * TODO Error printing might need a bit of cleanup Those tricks are used
		 * so that we are sure the compilation error is given to the user.
		 * Eclipse compiler wouldn't show it otherwise (as far as I can tell). I
		 * know it sucks, any better solution is welcome.
		 */

		Messager messager = processingEnv.getMessager();
		Throwable rootCause = e;
		while (rootCause.getCause() != null) {
			rootCause = rootCause.getCause();
		}

		StackTraceElement firstElement = e.getStackTrace()[0];
		StackTraceElement rootFirstElement = rootCause.getStackTrace()[0];
		String errorMessage = e.toString() + " " + firstElement.toString() + " root: " + rootCause.toString() + " " + rootFirstElement.toString();

		messager.printMessage(Diagnostic.Kind.ERROR, "Unexpected annotation processing exception: " + errorMessage);
		e.printStackTrace();

		Element element = roundEnv.getElementsAnnotatedWith(annotations.iterator().next()).iterator().next();
		messager.printMessage(Diagnostic.Kind.ERROR, "Unexpected annotation processing exception (not related to this element, but otherwise it wouldn't show up in eclipse) : " + errorMessage, element);
	}

}
