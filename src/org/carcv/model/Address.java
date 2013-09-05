/**
 * 
 */
package org.carcv.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * A basic getter/setter POJO implementation of IAddress, JPA annotated
 * @author oskopek
 *
 */
@Entity
@Table(name="address")
public class Address implements IAddress, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3193222849089726865L;

	private long id;

	private double latitude;
	
	private double longitude;
	
	private String city;
	
	private String postalcode;
	
	private String street;
	
	private String country;
	
	private int streetNumber;
	
	private int referenceNumber;

	/**
	 * For EJB
	 */
	public Address() {
		// hibernate empty constructor
	}
	

	/**
	 * @param latitude
	 * @param longitude
	 * @param city
	 * @param postalcode
	 * @param street
	 * @param country
	 * @param streetNumber
	 * @param referenceNumber
	 */
	public Address(double latitude, double longitude, String city,
			String postalcode, String street, String country, int streetNumber,
			int referenceNumber) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.city = city;
		this.postalcode = postalcode;
		this.street = street;
		this.country = country;
		this.streetNumber = streetNumber;
		this.referenceNumber = referenceNumber;
	}


	/**
	 * @param city
	 * @param postalcode
	 * @param street
	 * @param country
	 * @param streetNumber
	 * @param referenceNumber
	 */
	public Address(String city, String postalcode, String street,
			String country, int streetNumber, int referenceNumber) {
		this.latitude = 0;
		this.longitude = 0;
		
		this.city = city;
		this.postalcode = postalcode;
		this.street = street;
		this.country = country;
		this.streetNumber = streetNumber;
		this.referenceNumber = referenceNumber;
	}

	

	/**
	 * @param city
	 * @param postalcode
	 * @param street
	 * @param country
	 * @param streetNumber
	 */
	public Address(String city, String postalcode, String street,
			String country, int streetNumber) {
		this.latitude = 0;
		this.longitude = 0;
		
		this.city = city;
		this.postalcode = postalcode;
		this.street = street;
		this.country = country;
		this.streetNumber = streetNumber;
		
		this.referenceNumber = 0;
	}


	/* (non-Javadoc)
	 * @see org.carcv.model.ILocation#latitude()
	 */
	@Override
	@Column(name="latitude")
	@NotNull
	public double latitude() {
		return latitude;
	}

	/* (non-Javadoc)
	 * @see org.carcv.model.ILocation#longitude()
	 */
	@Override
	@Column(name="longitude")
	@NotNull
	public double longitude() {
		return longitude;
	}

	/* (non-Javadoc)
	 * @see org.carcv.model.IAddress#city()
	 */
	@Override
	@Column(name="city")
	@NotEmpty
	public String city() {
		return city;
	}

	/* (non-Javadoc)
	 * @see org.carcv.model.IAddress#postalcode()
	 */
	@Override
	@Column(name="postalcode")
	@NotEmpty
	public String postalcode() {
		return postalcode;
	}

	/* (non-Javadoc)
	 * @see org.carcv.model.IAddress#street()
	 */
	@Override
	@Column(name="street")
	@NotEmpty
	public String street() {
		return street;
	}

	/* (non-Javadoc)
	 * @see org.carcv.model.IAddress#streetNumber()
	 */
	@Override
	@Column(name="streetNumber")
	@NotNull
	public int streetNumber() {
		return streetNumber;
	}

	/* (non-Javadoc)
	 * @see org.carcv.model.IAddress#country()
	 */
	@Override
	@Column(name="country")
	@NotEmpty
	public String country() {
		return country;
	}

	/* (non-Javadoc)
	 * @see org.carcv.model.IAddress#referenceNumber()
	 */
	@Override
	@Column(name="referenceNumber")
	@NotNull
	public int referenceNumber() {
		return referenceNumber;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Address [id=" + id + ", latitude()=" + latitude() + ", longitude()="
				+ longitude() + ", city()=" + city() + ", postalcode()="
				+ postalcode() + ", street()=" + street() + ", streetNumber()="
				+ streetNumber() + ", country()=" + country()
				+ ", referenceNumber()=" + referenceNumber() + "]";
	}


	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}


	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}


	/**
	 * @param postalcode the postalcode to set
	 */
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}


	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}


	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}


	/**
	 * @param streetNumber the streetNumber to set
	 */
	public void setStreetNumber(int streetNumber) {
		this.streetNumber = streetNumber;
	}


	/**
	 * @param referenceNumber the referenceNumber to set
	 */
	public void setReferenceNumber(int referenceNumber) {
		this.referenceNumber = referenceNumber;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(latitude).append(longitude).append(city)
				.append(postalcode).append(street).append(country).append(streetNumber)
				.append(referenceNumber).toHashCode();
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
		if (!(obj instanceof Address)) {
			return false;
		}
		Address other = (Address) obj;
		
		return new EqualsBuilder().append(other.latitude, latitude).append(other.longitude, longitude).append(other.city, city)
				.append(other.postalcode, postalcode).append(other.street, street).append(other.country, country).append(other.streetNumber, streetNumber)
				.append(other.referenceNumber, referenceNumber).isEquals();
	}


	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue
	@NotNull
	@Column(name="id")
	public long getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	

}
