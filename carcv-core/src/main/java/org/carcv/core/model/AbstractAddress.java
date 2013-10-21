/**
 * 
 */
package org.carcv.core.model;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author oskopek
 *
 */
@MappedSuperclass
public abstract class AbstractAddress extends AbstractModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    
    @NotNull
    public abstract Double getLatitude();
    
    @NotNull
    public abstract Double getLongitude();
    
    @NotNull
    public abstract String getCity();
    
    @NotNull
    public abstract String getStreet();
    
    @NotNull
    public abstract String getCountry();
    
    @NotNull
    public abstract String getPostalCode();
    
    @NotNull
    public abstract Integer getStreetNumber();
    
    @NotNull
    public abstract Integer getReferenceNumber();
    
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getLatitude()).append(getLongitude())
                .append(getCity()).append(getPostalCode()).append(getStreet()).append(getCountry())
                .append(getStreetNumber()).append(getReferenceNumber()).toHashCode();
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
        if (!(obj instanceof AbstractAddress)) {
            return false;
        }
        AbstractAddress other = (AbstractAddress) obj;

        return new EqualsBuilder()
                .append(getLatitude(), other.getLatitude())
                .append(getLongitude(), other.getLongitude())
                .append(getCity(), other.getCity())
                .append(getPostalCode(), other.getPostalCode())
                .append(getStreet(), other.getStreet())
                .append(getCountry(), other.getCountry())
                .append(getStreetNumber(), other.getStreetNumber())
                .append(getReferenceNumber(), other.getReferenceNumber()).isEquals();
    }

    /**
     * Prints address in post-format
     * 
     * @return
     */
    public String print() {
        return getStreet() + " " + getStreetNumber() + "/"
                + getReferenceNumber() + "\n" + getPostalCode() + " "
                + getCity() + "\n" + getCountry();
    }

}
