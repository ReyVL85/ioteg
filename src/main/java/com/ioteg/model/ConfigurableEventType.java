package com.ioteg.model;

import java.util.concurrent.TimeUnit;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>
 * ConfigurableEventType class.
 * </p>
 *
 * @author antonio
 * @version $Id: $Id
 */
@Entity
public class ConfigurableEventType extends OwnedEntity{

	@NotNull
	@Valid
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private EventType eventType;
	@PositiveOrZero
	private Integer delay;
	@NotNull
	@Positive
	private Integer period;
	private TimeUnit unit;

	public ConfigurableEventType() {

	}

	/**
	 * <p>
	 * Constructor for ConfigurableEventType.
	 * </p>
	 * 
	 * @param id        a {@link java.lang.Long} object.
	 * @param eventType a {@link com.ioteg.model.EventType} object.
	 * @param delay     a {@link java.lang.Integer} object.
	 * @param period    a {@link java.lang.Integer} object.
	 * @param unit      a {@link java.util.concurrent.TimeUnit} object.
	 */
	public ConfigurableEventType(@JsonProperty("id") Long id, @NotNull @JsonProperty("eventtype") EventType eventType,
			@JsonProperty("delay") @PositiveOrZero Integer delay,
			@NotNull @Positive @JsonProperty("period") Integer period, @JsonProperty("unit") TimeUnit unit) {
		super();
		if (unit == null)
			unit = TimeUnit.SECONDS;
		if (delay == null)
			delay = 0;
		
		this.id = id;
		this.eventType = eventType;
		this.delay = delay;
		this.period = period;
		this.unit = unit;
	}

	/**
	 * <p>
	 * Getter for the field <code>eventType</code>.
	 * </p>
	 *
	 * @return a {@link com.ioteg.model.EventType} object.
	 */
	public EventType getEventType() {
		return eventType;
	}

	/**
	 * <p>
	 * Setter for the field <code>eventType</code>.
	 * </p>
	 *
	 * @param eventType a {@link com.ioteg.model.EventType} object.
	 */
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	/**
	 * <p>
	 * Getter for the field <code>delay</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	public Integer getDelay() {
		return delay;
	}

	/**
	 * <p>
	 * Setter for the field <code>delay</code>.
	 * </p>
	 *
	 * @param delay a {@link java.lang.Integer} object.
	 */
	public void setDelay(Integer delay) {
		this.delay = delay;
	}

	/**
	 * <p>
	 * Getter for the field <code>period</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	public Integer getPeriod() {
		return period;
	}

	/**
	 * <p>
	 * Setter for the field <code>period</code>.
	 * </p>
	 *
	 * @param period a {@link java.lang.Integer} object.
	 */
	public void setPeriod(Integer period) {
		this.period = period;
	}

	/**
	 * <p>
	 * Getter for the field <code>unit</code>.
	 * </p>
	 *
	 * @return a {@link java.util.concurrent.TimeUnit} object.
	 */
	public TimeUnit getUnit() {
		return unit;
	}

	/**
	 * <p>
	 * Setter for the field <code>unit</code>.
	 * </p>
	 *
	 * @param unit a {@link java.util.concurrent.TimeUnit} object.
	 */
	public void setUnit(TimeUnit unit) {
		this.unit = unit;
	}
	
	

}
