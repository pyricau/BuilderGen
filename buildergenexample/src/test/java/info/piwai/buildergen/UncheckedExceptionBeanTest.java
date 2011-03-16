package info.piwai.buildergen;

import info.piwai.buildergen.bean.UncheckedExceptionBeanBuilder;

import org.junit.Test;

public class UncheckedExceptionBeanTest {
	
	@Test(expected=IllegalArgumentException.class)
	public void throwsConstructorException() {
		UncheckedExceptionBeanBuilder.create().build();
	}

}
