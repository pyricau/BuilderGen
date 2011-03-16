package info.piwai.buildergen.bean;

import info.piwai.buildergen.api.Buildable;

@Buildable
public class UncheckedExceptionBean {
	
	public UncheckedExceptionBean() throws IllegalStateException {
		throw new IllegalArgumentException();
	}

}
