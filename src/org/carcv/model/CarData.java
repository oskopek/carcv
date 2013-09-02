/**
 * 
 */
package org.carcv.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * An expression of Data collected
 * @author oskopek
 *
 */
@Entity
public class CarData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2135634243340403587L;

	@Id
	@GeneratedValue
	@NotNull
	private long id;
	
	@ManyToOne
	@NotNull
	private Speed speed;
	
	@ManyToOne
	@NotNull
	private ILocation location;
	
	@ManyToOne
	@NotNull
	private LicencePlate licencePlate;
	
	@OneToOne
	@NotNull
	private Date timestamp;
	
	@OneToOne
	@NotNull
	private MediaObject video;
	
	
	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private CarData() {
		//hibernate stub
	}


	/**
	 * @param speed
	 * @param location
	 * @param licencePlate
	 * @param timestamp
	 * @param video
	 */
	public CarData(Speed speed, ILocation location, LicencePlate licencePlate,
			Date timestamp, MediaObject video) {
		this.speed = speed;
		this.location = location;
		this.licencePlate = licencePlate;
		this.timestamp = timestamp;
		this.video = video;
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


	/**
	 * @return the speed
	 */
	public Speed getSpeed() {
		return speed;
	}


	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(Speed speed) {
		this.speed = speed;
	}


	/**
	 * @return the location
	 */
	public ILocation getLocation() {
		return location;
	}


	/**
	 * @param location the location to set
	 */
	public void setLocation(ILocation location) {
		this.location = location;
	}


	/**
	 * @return the licencePlate
	 */
	public LicencePlate getLicencePlate() {
		return licencePlate;
	}


	/**
	 * @param licencePlate the licencePlate to set
	 */
	public void setLicencePlate(LicencePlate licencePlate) {
		this.licencePlate = licencePlate;
	}


	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}


	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}


	/**
	 * @return the video
	 */
	public MediaObject getVideo() {
		return video;
	}


	/**
	 * @param video the video to set
	 */
	public void setVideo(MediaObject video) {
		this.video = video;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CarData [id=" + id + ", speed=" + speed + ", location=" + location
				+ ", licencePlate=" + licencePlate + ", timestamp=" + timestamp
				+ ", video=" + video + "]";
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(speed).append(location).append(licencePlate)
				.append(timestamp).append(video).toHashCode();
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
		if (!(obj instanceof CarData)) {
			return false;
		}
		CarData other = (CarData) obj;
		
		return new EqualsBuilder().append(speed, other.speed).append(location, other.location)
				.append(licencePlate, other.licencePlate).append(timestamp, other.timestamp)
				.append(video, other.video).isEquals();
	}
	
	
	
	

}
