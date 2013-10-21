/**
 * 
 */
package org.carcv.core.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author oskopek
 * 
 */
@Entity
public class Speed extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1816208535143255888L;


	@SuppressWarnings("unused")
    private Speed() {
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

	
    @NotNull
    //@Range(min = 0, message = "Speed is less or equal to 0!")
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

    @NotNull
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
	

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getSpeed()).append(getUnit()).toHashCode();
    }

    /*
     * (non-Javadoc)
     * 
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
        return new EqualsBuilder()
                .append(this.getSpeed(), other.getSpeed())
                .append(this.getUnit(), other.getUnit())
                .isEquals();
    }

}
