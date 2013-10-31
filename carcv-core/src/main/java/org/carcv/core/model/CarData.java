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
 */
@Embeddable
public class CarData extends AbstractEmbeddable implements Comparable<CarData>, Cloneable {

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
    public CarData(Speed speed, Address address, NumberPlate numberPlate, Date timestamp) {
        this.speed = speed;
        this.address = address;
        this.numberPlate = numberPlate;
        this.timestamp = timestamp;
    }

    @NotNull
    @Embedded
    public Speed getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
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
     * @param location the location to set
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
     * @param numberPlate the numberPlate to set
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
     * @param timestamp the timestamp to set
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
     * @Override public String toString() { return "CarData [id=" + getId() + ", speed=" + speed + ", location=" + location +
     * ", numberPlate=" + numberPlate + ", timestamp=" + timestamp + ", video=" + video + "]"; }
     */

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getSpeed()).append(getAddress()).append(getNumberPlate())
            .append(getTimestamp()).toHashCode();
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

        return new EqualsBuilder().append(getSpeed(), other.getSpeed()).append(getAddress(), other.getAddress())
            .append(getNumberPlate(), other.getNumberPlate()).append(getTimestamp(), other.getTimestamp())
            .isEquals();
    }

    @Override
    public int compareTo(CarData o) {
        return new CompareToBuilder().append(speed, o.speed).append(address, o.address)
            .append(numberPlate, o.numberPlate).append(timestamp, o.timestamp).toComparison();
    }

    @Override
    public Object clone() {
        Speed s = getSpeed() == null ? null : new Speed(getSpeed().getSpeed(), getSpeed().getUnit());
        Address a = getAddress() == null ? null : new Address(address.getCity(), address.getPostalCode(), address.getStreet(),
            address.getCountry(), address.getStreetNumber(), address.getReferenceNumber());
        NumberPlate np = getNumberPlate() == null ? null : new NumberPlate(getNumberPlate().getText(), getNumberPlate()
            .getOrigin());

        return new CarData(s, a, np, (Date) getTimestamp().clone());
    }

}