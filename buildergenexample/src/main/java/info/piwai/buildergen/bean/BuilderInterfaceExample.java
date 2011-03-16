package info.piwai.buildergen.bean;

import info.piwai.buildergen.api.Builder;
import info.piwai.buildergen.api.UncheckedBuilder;

import java.util.ArrayList;
import java.util.List;

public class BuilderInterfaceExample {

	public <T> List<T> buildMultipleInstances(Builder<T> builder, int numberOfInstances) throws Exception {
		List<T> instances = new ArrayList<T>();
		for (int i = 0; i < numberOfInstances; i++) {
			instances.add(builder.build());
		}
		return instances;
	}
	
	public <T> List<T> buildMultipleInstances(UncheckedBuilder<T> builder, int numberOfInstances) {
		List<T> instances = new ArrayList<T>();
		for (int i = 0; i < numberOfInstances; i++) {
			instances.add(builder.build());
		}
		return instances;
	}
}
