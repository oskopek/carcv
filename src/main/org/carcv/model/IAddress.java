/**
 * 
 */
package org.carcv.model;

/**
 * Interface of a simple address
 * @author oskopek
 *
 */
public interface IAddress extends ILocation {
	
	/**
	 * 
	 * @return City name
	 */
	public String city();
	
	/**
	 * 
	 * @return Postal Code
	 */
	public String postalcode();
	
	/**
	 * 
	 * @return Street name
	 */
	public String street();
	
	/**
	 * 
	 * @return Street number (House number)
	 */
	public int streetNumber();
	
	/**
	 * 
	 * @return Country name
	 */
	public String country();
	
	/**
	 * 
	 * @return House reference number (city-wide)
	 */
	public int referenceNumber();
}
