package info.piwai.buildergen;

import info.piwai.buildergen.bean.CheckedExceptionBeanBuilder;

import java.io.IOException;

import org.junit.Test;

public class CheckedExceptionBeanTest {
	
	@Test(expected=IOException.class)
	public void throwsConstructorException() throws IOException {
		CheckedExceptionBeanBuilder.create().build();
	}

}
