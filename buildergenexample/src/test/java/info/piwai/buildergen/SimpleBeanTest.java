package info.piwai.buildergen;

import junit.framework.Assert;
import info.piwai.buildergen.bean.SimpleBean;
import info.piwai.buildergen.bean.SimpleBeanBuilder;

import org.junit.Test;

public class SimpleBeanTest {

	@Test
	public void newInstanceNotNull() {
		Assert.assertNotNull(SimpleBeanBuilder.create().build());
	}
	
	@Test
	public void notSameInstanceTwice() {
		SimpleBeanBuilder builder = SimpleBeanBuilder.create();
		
		SimpleBean bean1 = builder.build();
		SimpleBean bean2 = builder.build();
		
		Assert.assertNotSame(bean1, bean2);
	}

}
