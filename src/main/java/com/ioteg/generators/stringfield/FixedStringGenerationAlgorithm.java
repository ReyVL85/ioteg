package com.ioteg.generators.stringfield;

import com.ioteg.generators.GenerationAlgorithm;
import com.ioteg.model.Field;

public class FixedStringGenerationAlgorithm implements GenerationAlgorithm<String> {

	@Override
	public String generate(Field stringField) {
		return stringField.getValue();
	}

}