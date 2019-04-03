package com.ioteg.services.periodicgeneration;

import java.text.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ioteg.exprlang.ExprParser.ExprLangParsingException;
import com.ioteg.generation.EventTypeGenerationAlgorithm;
import com.ioteg.generation.EventTypeGenerator;
import com.ioteg.generation.GenerationContext;
import com.ioteg.generation.NotExistingGeneratorException;
import com.ioteg.model.EventType;

/**
 * <p>PeriodicEventGenerator class.</p>
 *
 * @author antonio
 * @version $Id: $Id
 */
public class PeriodicEventGenerator implements Runnable {
	private EventTypeGenerator eventTypeGenerator;
	private Logger logger = LoggerFactory.getLogger(PeriodicEventGenerator.class);
	private ObjectMapper objectMapper;
	/**
	 * <p>Constructor for PeriodicEventGenerator.</p>
	 *
	 * @param eventType a {@link com.ioteg.model.EventType} object.
	 * @throws java.text.ParseException if any.
	 * @throws com.ioteg.exprlang.ExprParser.ExprLangParsingException if any.
	 * @throws com.ioteg.generation.NotExistingGeneratorException if any.
	 * @param generationContext a {@link com.ioteg.generation.GenerationContext} object.
	 * @param objectMapper a {@link com.fasterxml.jackson.databind.ObjectMapper} object.
	 */
	public PeriodicEventGenerator(EventType eventType, GenerationContext generationContext, ObjectMapper objectMapper) throws NotExistingGeneratorException, ExprLangParsingException, ParseException {
		this.eventTypeGenerator = new EventTypeGenerator(new EventTypeGenerationAlgorithm(eventType, generationContext), generationContext);
		this.objectMapper = objectMapper;
		this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	}

	/** {@inheritDoc} */
	@Override
	public void run() {
		try {
			logger.info(objectMapper.writeValueAsString(eventTypeGenerator.generate(1)));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	


}