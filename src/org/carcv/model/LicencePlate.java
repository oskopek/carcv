/**
 * 
 */
package org.carcv.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Country of origin and text of a given Licence Plate
 * @author oskopek
 *
 */
@Entity
public class LicencePlate implements Serializable, Comparable<LicencePlate> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2507938473851975932L;
	
	@Id
	@GeneratedValue
	@NotNull
	private long id;

	@Column
	@NotNull
	private String text;
	
	@Column
	private String origin;
	
	@SuppressWarnings("unused")
	private LicencePlate() {
		//hibernate stub
	}

	/**
	 * @param text
	 * @param origin
	 */
	public LicencePlate(String text, String origin) {
		this.text = text;
		this.origin = origin;
	}
	
	/**
	 * @param text
	 */
	public LicencePlate(String text) {
		this.text = text;
		this.origin = "";
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LicencePlate [text=" + text + ", origin=" + origin + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(text).append(origin).toHashCode();
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
		if (!(obj instanceof LicencePlate)) {
			return false;
		}
		LicencePlate other = (LicencePlate) obj;
		return new EqualsBuilder().append(text, other.text).append(origin, other.origin).isEquals();
	}

	@Override
	public int compareTo(LicencePlate o) {
		return new CompareToBuilder().append(text, o.text).append(origin, o.origin).toComparison();
	}
	
	
	
	

}
