/**
 * 
 */
package org.carcv.core.model;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * An expression of Data collected
 * 
 * @author oskopek
 * 
 */
@Embeddable
public class CarData extends AbstractEmbeddable implements Comparable<CarData> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2135634243340403587L;

	private Speed speed;

	private Address address;

	private NumberPlate numberPlate;

	private Date timestamp;

	@SuppressWarnings("unused")
    private CarData() {
		// hibernate stub
	}

	/**
	 * @param speed
	 * @param address
	 * @param numberPlate
	 * @param timestamp
	 * @param video
	 */
	public CarData(Speed speed, Address address, NumberPlate numberPlate,
			Date timestamp) {
		this.speed = speed;
		this.address  = address;
		this.numberPlate = numberPlate;
		this.timestamp = timestamp;
	}
	
	
	@NotNull
	@Embedded
	public Speed getSpeed() {
		return speed;
	}

	/**
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(Speed speed) {
		this.speed = speed;
	}


    @NotNull
	@Embedded
	public Address getAddress() {
		return address;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

    @NotNull
    @Embedded
	public NumberPlate getNumberPlate() {
		return numberPlate;
	}

	/**
	 * @param numberPlate
	 *            the numberPlate to set
	 */
	public void setNumberPlate(NumberPlate numberPlate) {
		this.numberPlate = numberPlate;
	}

	/**
	 * @return the timestamp
	 */
    @NotNull
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp
	 *            the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	/*
	@Override
	public String toString() {
		return "CarData [id=" + getId() + ", speed=" + speed + ", location="
				+ location + ", numberPlate=" + numberPlate + ", timestamp="
				+ timestamp + ", video=" + video + "]";
	}
	*/
	

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getSpeed()).append(getAddress())
                .append(getNumberPlate()).append(getTimestamp())
                .toHashCode();
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
        if (!(obj instanceof CarData)) {
            return false;
        }
        CarData other = (CarData) obj;

        return new EqualsBuilder()
                .append(getSpeed(), other.getSpeed())
                .append(getAddress(), other.getAddress())
                .append(getNumberPlate(), other.getNumberPlate())
                .append(getTimestamp(), other.getTimestamp())
                .isEquals();
    }

    @Override
    public int compareTo(CarData o) {
        return new CompareToBuilder()
                .append(speed, o.speed)
                .append(address, o.address)
                .append(numberPlate, o.numberPlate)
                .append(timestamp, o.timestamp)
                .toComparison();
    }

}
