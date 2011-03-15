package info.piwai.buildergen;

import java.io.IOException;

import info.piwai.buildergen.bean.ExceptionBeanBuilder;

import org.junit.Test;

public class ExceptionBeanTest {
	
	@Test(expected=IOException.class)
	public void throwsConstructorException() throws IOException {
		ExceptionBeanBuilder.create().build();
	}

}
