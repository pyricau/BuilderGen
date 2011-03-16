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
package info.piwai.buildergen.generation;

import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.processing.Filer;
import javax.tools.JavaFileObject;

import org.junit.Test;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JPackage;

/**
 * @author Pierre-Yves Ricau (py.ricau at gmail.com) *
 */
public class SourceCodeWriterTest {

	@Test
	public void usesFilerToOpenStream() throws IOException {

		Filer filer = mock(Filer.class);
		JavaFileObject fileObject = mock(JavaFileObject.class);
		when(filer.createSourceFile(anyString())).thenReturn(fileObject);

		OutputStream expectedOS = mock(OutputStream.class);
		when(fileObject.openOutputStream()).thenReturn(expectedOS);

		SourceCodeWriter sourceCodeWriter = new SourceCodeWriter(filer);
		JPackage jPackage = new JCodeModel()._package("some.package");
		OutputStream resultingOS = sourceCodeWriter.openBinary(jPackage, "SomeClass.java");

		assertSame(expectedOS, resultingOS);
	}

	@Test
	public void doesNotCallOutputStreamCloseMethod() throws IOException {

		Filer filer = mock(Filer.class);
		JavaFileObject fileObject = mock(JavaFileObject.class);
		when(filer.createSourceFile(anyString())).thenReturn(fileObject);

		OutputStream expectedOS = mock(OutputStream.class);
		when(fileObject.openOutputStream()).thenReturn(expectedOS);

		SourceCodeWriter sourceCodeWriter = new SourceCodeWriter(filer);
		JPackage jPackage = new JCodeModel()._package("some.package");
		sourceCodeWriter.openBinary(jPackage, "SomeClass.java");

		doThrow(new RuntimeException("Close method should not be called")).when(expectedOS).close();

		sourceCodeWriter.close();
	}

	@Test
	public void multipleCallsAreDelegatedToFiler() throws IOException {
		Filer filer = mock(Filer.class);

		JavaFileObject fileObject = mock(JavaFileObject.class);
		when(filer.createSourceFile(anyString())).thenReturn(fileObject);

		SourceCodeWriter sourceCodeWriter = new SourceCodeWriter(filer);
		JPackage jPackage = new JCodeModel()._package("some.package");
		sourceCodeWriter.openBinary(jPackage, "SomeClass.java");
		sourceCodeWriter.openBinary(jPackage, "SomeClass.java");

		verify(filer, times(2)).createSourceFile(anyString());

	}

}
