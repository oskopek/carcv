/**
 * 
 */
package org.carcv.impl.core.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.carcv.core.model.AbstractCarData;

/**
 * An expression of Data collected
 * 
 * @author oskopek
 * 
 */
@Entity
public class CarData extends AbstractCarData {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2135634243340403587L;

	private Speed speed;

	private Address address;

	private NumberPlate numberPlate;

	private Date timestamp;

	/**
	 * For EJB
	 */
	public CarData() {
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
	
	
	@Override
	@OneToOne
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


    @Override
	@OneToOne
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

	@Override
    @OneToOne
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
	@Override
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

}
