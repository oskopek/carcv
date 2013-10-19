/**
 * 
 */
package org.carcv.impl.core.model;

import javax.persistence.Entity;

import org.carcv.core.model.AbstractAddress;

/**
 * A basic getter/setter POJO implementation of IAddress, JPA annotated
 * 
 * @author oskopek
 * 
 */
@Entity
public class Address extends AbstractAddress {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3193222849089726865L;

	private Double latitude;

	private Double longitude;

	private String city;

	private String postalCode;

	private String street;

	private String country;

	private Integer streetNumber;

	private Integer referenceNumber;

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
	public Address(Double latitude, Double longitude, String city,
			String postalcode, String street, String country, Integer streetNumber,
			Integer referenceNumber) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.city = city;
		this.postalCode = postalcode;
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
			String country, Integer streetNumber, Integer referenceNumber) {
		this.latitude = 0d;
		this.longitude = 0d;

		this.city = city;
		this.postalCode = postalcode;
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
			String country, Integer streetNumber) {
		this.latitude = 0d;
		this.longitude = 0d;

		this.city = city;
		this.postalCode = postalcode;
		this.street = street;
		this.country = country;
		this.streetNumber = streetNumber;

		this.referenceNumber = 0;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @param postalcode
	 *            the postalcode to set
	 */
	public void setPostalCode(String postalcode) {
		this.postalCode = postalcode;
	}

	/**
	 * @param street
	 *            the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @param streetNumber
	 *            the streetNumber to set
	 */
	public void setStreetNumber(int streetNumber) {
		this.streetNumber = streetNumber;
	}

	/**
	 * @param referenceNumber
	 *            the referenceNumber to set
	 */
	public void setReferenceNumber(int referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

    @Override
    public Double getLatitude() {
        return latitude;
    }

    @Override
    public Double getLongitude() {
        return longitude;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getStreet() {
        return street;
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public Integer getStreetNumber() {
        return streetNumber;
    }

    @Override
    public Integer getReferenceNumber() {
        return referenceNumber;
    }

}
