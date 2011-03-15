package info.piwai.buildergen.bean;

import info.piwai.buildergen.api.Buildable;

import java.io.IOException;

@Buildable
public class ExceptionBean {
	
	public ExceptionBean() throws IOException {
		throw new IOException();
	}

}
