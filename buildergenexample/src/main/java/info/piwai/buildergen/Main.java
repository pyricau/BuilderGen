package info.piwai.buildergen;

import info.piwai.buildergen.bean.MyBean;
import info.piwai.buildergen.bean.MyBeanBuilder;

public class Main {
	
	public static void main(String[] args) {
		MyBean myBean = MyBeanBuilder.create().build();
		
		
	}

}
