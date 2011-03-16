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

import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.processing.Filer;
import javax.tools.JavaFileObject;

import com.sun.codemodel.CodeWriter;
import com.sun.codemodel.JPackage;

/**
 * A binder between APT and CodeModel : CodeModel needs an {@link OutputStream} to
 * generate sources. This {@link OutputStream} is opened using the APT {@link Filer} util.
 * 
 * @author Pierre-Yves Ricau (py.ricau at gmail.com)
 */
public class SourceCodewriter extends CodeWriter {

	private final Filer filer;

	public SourceCodewriter(Filer filer) {
		this.filer = filer;
	}

	@Override
	public OutputStream openBinary(JPackage pkg, String fileName) throws IOException {
		String qualifiedClassName = toQualifiedClassName(pkg, fileName);

		JavaFileObject sourceFile = filer.createSourceFile(qualifiedClassName);

		return sourceFile.openOutputStream();
	}

	private String toQualifiedClassName(JPackage pkg, String fileName) {
		int suffixPosition = fileName.lastIndexOf('.');
		String className = fileName.substring(0, suffixPosition);
		String qualifiedClassName = pkg.name() + "." + className;
		return qualifiedClassName;
	}

	@Override
	public void close() throws IOException {
	}
}