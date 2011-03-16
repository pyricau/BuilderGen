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
package info.piwai.buildergen.validation;

/**
 * Helper class to deal with {@link BuildableValidator} validation state. The
 * {@link #isValid()} method always return true, until the {@link #invalidate()}
 * method has been called.
 * 
 * @author Pierre-Yves Ricau (py.ricau at gmail.com)
 */
public class IsValid {

	private boolean valid = true;

	/**
	 * Invalidates this instance of {@link IsValid}. After {@link #invalidate()}
	 * has been called , {@link IsValid} will no longer return true.
	 */
	public void invalidate() {
		valid = false;
	}

	/**
	 * @return true as long as the {@link #invalidate()} method has not been
	 *         called.
	 */
	public boolean isValid() {
		return valid;
	}
}
