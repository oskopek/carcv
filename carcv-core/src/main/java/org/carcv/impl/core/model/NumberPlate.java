/**
 * 
 */
package org.carcv.impl.core.model;

import org.carcv.core.model.AbstractNumberPlate;

/**
 * Country of origin and text of a given Licence Plate
 * 
 * @author oskopek
 * 
 */
public class NumberPlate extends AbstractNumberPlate {

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

    @Override
    public String getText() {
        return text;
    }

    @Override
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

}
