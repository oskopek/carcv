/**
 * 
 */
package org.carcv.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Range;

/**
 * @author oskopek
 *
 */
@Entity
public class Speed implements Serializable, Comparable<Speed> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1816208535143255888L;

	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private Speed() {
		//hibernate constructor stub
	}
	
	/**
	 * @param speed
	 * @param unit
	 */
	public Speed(double speed, SpeedUnit unit) {
		this.speed = speed;
		this.unit = unit;
	}

	/**
	 * Default unit = SpeedUnit.MS (meters per second)
	 * @param speed
	 */
	public Speed(double speed) {
		this.speed = speed;
		this.unit = SpeedUnit.MS;
	}

	@Id
	@GeneratedValue
	@NotNull
	private long id;

	@Column
	@NotNull
	@Range(min=0,message="Speed is less or equal to 0!")
	private double speed;
	
	@Column
	@NotNull
	private SpeedUnit unit;
	
	/**
	 * @return the speed
	 */
	public double getSpeed() {
		return speed;
	}
	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	/**
	 * @return the unit
	 */
	public SpeedUnit getUnit() {
		return unit;
	}
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(SpeedUnit unit) {
		this.unit = unit;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Speed [speed=" + speed + ", unit=" + unit + "]";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(speed).append(unit).toHashCode();
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Speed)) {
			return false;
		}
		Speed other = (Speed) obj;
		return new EqualsBuilder().append(other.speed, other.unit).isEquals();
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public int compareTo(Speed o) {
		return new CompareToBuilder().append(speed, o.speed).toComparison();
	}
	
	

}
