/**
 * 
 */
package org.carcv.core.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Country of origin and text of a given Licence Plate
 * 
 * @author oskopek
 * 
 */
@Entity
public class NumberPlate extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2507938473851975932L;

	private String text;

	private String origin;

	public NumberPlate() {
		// hibernate stub
	}

	/**
	 * @param text
	 * @param origin
	 */
	public NumberPlate(String text, String origin) {
		this.text = text;
		this.origin = origin;
	}

	/**
	 * @param text
	 */
	public NumberPlate(String text) {
		this.text = text;
		this.origin = "";
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @param origin
	 *            the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

    @NotNull
    public String getText() {
        return text;
    }

    @NotNull
    public String getOrigin() {
        return origin;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	/*
	@Override
	public String toString() {
		return "NumberPlate [text=" + text + ", origin=" + origin + "]";
	}*/
    
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getText()).append(getOrigin()).toHashCode();
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
        if (!(obj instanceof NumberPlate)) {
            return false;
        }
        NumberPlate other = (NumberPlate) obj;
        return new EqualsBuilder()
                .append(getText(), other.getText())
                .append(getOrigin(), other.getOrigin())
                .isEquals();
    }

}
