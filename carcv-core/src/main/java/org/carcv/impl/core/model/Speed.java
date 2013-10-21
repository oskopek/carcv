/**
 * 
 */
package org.carcv.impl.core.model;

import javax.persistence.Entity;

import org.carcv.core.model.AbstractSpeed;
import org.carcv.core.model.SpeedUnit;

/**
 * @author oskopek
 * 
 */
@Entity
public class Speed extends AbstractSpeed {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1816208535143255888L;

	/**
	 * For EJB
	 */
	public Speed() {
		// hibernate constructor stub
	}

	/**
	 * @param speed
	 * @param unit
	 */
	public Speed(Double speed, SpeedUnit unit) {
		this.speed = speed;
		this.unit = unit;
	}

	/**
	 * Default unit = SpeedUnit.MS (meters per second)
	 * 
	 * @param speed
	 */
	public Speed(Double speed) {
		this.speed = speed;
		this.unit = SpeedUnit.MS;
	}

	private Double speed;

	private SpeedUnit unit;

	@Override
	public Double getSpeed() {
		return speed;
	}

	/**
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	@Override
	public SpeedUnit getUnit() {
		return unit;
	}

	/**
	 * @param unit
	 *            the unit to set
	 */
	public void setUnit(SpeedUnit unit) {
		this.unit = unit;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	/*
	@Override
	public String toString() {
		return "Speed [speed=" + speed + ", unit=" + unit + "]";
	}*/

}
