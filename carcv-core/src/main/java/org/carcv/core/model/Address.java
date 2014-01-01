/*
 * Copyright 2012 CarCV Development Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.carcv.core.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * A basic implementation of an Address.
 * <p>
 * Provides basic getters and setters only.
 */
@Embeddable
public class Address extends AbstractEmbeddable implements Comparable<Address> {

    private static final long serialVersionUID = 3193222849089726865L;

    private Double latitude;
    private Double longitude;
    private String city;
    private String postalCode;
    private String street;
    private String country;
    private Integer streetNumber;
    private Integer referenceNumber;

    @SuppressWarnings("unused")
    private Address() {
        // hibernate empty constructor
    }

    /**
     * Creates an initialized Address object.
     *
     * @param latitude the latitude to set
     * @param longitude the longitude to set
     * @param city the city to set
     * @param postalcode the postal code to set
     * @param street the street to set
     * @param country the country to set
     * @param streetNumber the street number to set
     * @param referenceNumber the reference number to set
     */
    public Address(Double latitude, Double longitude, String city, String postalcode, String street, String country,
        Integer streetNumber, Integer referenceNumber) {
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
     * Creates an initialized Address object.
     * <p>
     * The latitude and longitude are initialized to 0d.
     *
     * @param city the city to set
     * @param postalcode the postal code to set
     * @param street the street to set
     * @param country the country to set
     * @param streetNumber the street number to set
     * @param referenceNumber the reference number to set
     */
    public Address(String city, String postalcode, String street, String country, Integer streetNumber,
        Integer referenceNumber) {
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
     * Creates an initialized Address object.
     * <p>
     * The latitude and longitude are initialized to 0d. The referenceNumber is initialized to 0.
     *
     * @param city the city to set
     * @param postalcode the postal code to set
     * @param street the street to set
     * @param country the country to set
     * @param streetNumber the street number to set
     */
    public Address(String city, String postalcode, String street, String country, Integer streetNumber) {
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
     * @param postalcode the postal code to set
     */
    public void setPostalCode(String postalcode) {
        this.postalCode = postalcode;
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
     * @param streetNumber the street number to set
     */
    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    /**
     * @param referenceNumber the reference number to set
     */
    public void setReferenceNumber(int referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    /**
     * @return the latitude
     */
    @NotNull
    public Double getLatitude() {
        return latitude;
    }

    /**
     * @return the longitude
     */
    @NotNull
    public Double getLongitude() {
        return longitude;
    }

    /**
     * @return the city
     */
    @NotNull
    public String getCity() {
        return city;
    }

    /**
     * @return the street
     */
    @NotNull
    public String getStreet() {
        return street;
    }

    /**
     * @return the country
     */
    @NotNull
    public String getCountry() {
        return country;
    }

    /**
     * @return the postal code
     */
    @NotNull
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @return the street number
     */
    @NotNull
    public Integer getStreetNumber() {
        return streetNumber;
    }

    /**
     * @return the reference number
     */
    @NotNull
    public Integer getReferenceNumber() {
        return referenceNumber;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getLatitude()).append(getLongitude()).append(getCity())
            .append(getPostalCode()).append(getStreet()).append(getCountry()).append(getStreetNumber())
            .append(getReferenceNumber()).toHashCode();
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
        if (!(obj instanceof Address)) {
            return false;
        }
        Address other = (Address) obj;

        return new EqualsBuilder().append(getLatitude(), other.getLatitude())
            .append(getLongitude(), other.getLongitude()).append(getCity(), other.getCity())
            .append(getPostalCode(), other.getPostalCode()).append(getStreet(), other.getStreet())
            .append(getCountry(), other.getCountry()).append(getStreetNumber(), other.getStreetNumber())
            .append(getReferenceNumber(), other.getReferenceNumber()).isEquals();
    }

    /**
     * Prints the Address in a post-compliant format, lines ending with '\n'
     *
     * @see #printBR()
     * @return a String representation of the Address separated by '\n'
     */
    public String print() {
        return printDelimeter("\n");
    }

    /**
     * Prints the Address with the '<code>&lt;br&gt;</code>' tag instead of '\n'
     * <p>
     * To be used in HTML pages.
     *
     * @see #print()
     * @return a String representation of the Address separated by '<code>&lt;br&gt;</code>'
     */
    public String printBR() {
        return printDelimeter("<br>");
    }

    /**
     * Prints the Address with the <code>delimeter</code> as each new line character.
     *
     * @param delimeter the String to place as new line characters
     * @return a post-compliant formatted String representation of the Address
     */
    private String printDelimeter(String delimeter) {
        return getStreet() + " " + getStreetNumber() + "/" + getReferenceNumber() + delimeter + getPostalCode() + " "
            + getCity() + delimeter + getCountry();
    }

    @Override
    public int compareTo(Address other) {
        return new CompareToBuilder().append(getLatitude(), other.getLatitude())
            .append(getLongitude(), other.getLongitude()).append(getCity(), other.getCity())
            .append(getPostalCode(), other.getPostalCode()).append(getStreet(), other.getStreet())
            .append(getCountry(), other.getCountry()).append(getStreetNumber(), other.getStreetNumber())
            .append(getReferenceNumber(), other.getReferenceNumber()).toComparison();
    }

    @Override
    public String toString() {
        return printDelimeter(", ");
    }
}