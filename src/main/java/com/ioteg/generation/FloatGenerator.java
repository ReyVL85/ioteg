package com.ioteg.generation;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.model.Field;
import com.ioteg.resultmodel.ResultField;
import com.ioteg.resultmodel.ResultSimpleField;

/**
 * <p>FloatGenerator class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class FloatGenerator extends FieldGenerator<Float> {

	/**
	 * <p>Constructor for FloatGenerator.</p>
	 *
	 * @param generationAlgorithm a {@link com.ioteg.generation.GenerationAlgorithm} object.
	 * @param field a {@link com.ioteg.model.Field} object.
	 * @param generationContext a {@link com.ioteg.generation.GenerationContext} object.
	 */
	public FloatGenerator(GenerationAlgorithm<Float> generationAlgorithm, Field field,
			GenerationContext generationContext) {
		super(generationAlgorithm, field, generationContext);
	}

	/** {@inheritDoc} */
	@Override
	public List<ResultField> generate(Integer numberOfRequiredItems)
			throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		List<ResultField> results = new ArrayList<>();

		if (field.getPrecision() != null) {
			for (int i = 0; i < numberOfRequiredItems; ++i) {
				Float result = generationAlgorithm.generate();
				results.add(new ResultSimpleField(field.getName(), field.getType(), field.getQuotes(),
						numberToSpecifiedPrecision(result, field.getPrecision())));
			}

			if(field.getInjectable())
				generationContext.putInjectableResultField(field.getName(), results.get(results.size() - 1));
		}

		else
			results = super.generate(numberOfRequiredItems);

		return results;
	}

	private String numberToSpecifiedPrecision(Float floatNumber, Integer precision) {
		String format = "%." + precision + "f";
		return String.format(Locale.US, format, floatNumber);
	}

}
