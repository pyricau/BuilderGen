package info.piwai.buildergen.bean;

import info.piwai.buildergen.api.Buildable;

import java.io.IOException;

@Buildable
public class CheckedExceptionBean {
	
	public CheckedExceptionBean() throws IOException {
		throw new IOException();
	}

}
