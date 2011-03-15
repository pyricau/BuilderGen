package info.piwai.buildergen.bean;

import info.piwai.buildergen.api.Build;
import info.piwai.buildergen.api.Buildable;

@Buildable
public class SimpleBean {
	
	protected SimpleBean() {
		
	}
	
	@Build
	protected SimpleBean(int test) {
		
	}

}
