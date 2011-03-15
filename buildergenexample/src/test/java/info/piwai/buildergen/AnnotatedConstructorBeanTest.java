package info.piwai.buildergen;

import info.piwai.buildergen.bean.AnnotatedConstructorBean;
import info.piwai.buildergen.bean.AnnotatedConstructorBeanBuilder;

import org.junit.Assert;
import org.junit.Test;

public class AnnotatedConstructorBeanTest {
	
	@Test
	public void annotatedConstructorIsUsed() {
		AnnotatedConstructorBean bean = AnnotatedConstructorBeanBuilder.create().field1("hello").build();
		
		Assert.assertEquals("hello", bean.getField1());
		Assert.assertEquals("yeah", bean.getField2());
	}

}
