/**
 * 
 */
package org.carcv.model;

/**
 * Interface of a simple address
 * 
 * @author oskopek
 * 
 */
public interface IAddress extends ILocation {

	/**
	 * 
	 * @return City name
	 */
	public String getCity();

	/**
	 * 
	 * @return Postal Code
	 */
	public String getPostalCode();

	/**
	 * 
	 * @return Street name
	 */
	public String getStreet();

	/**
	 * 
	 * @return Street number (House number)
	 */
	public int getStreetNumber();

	/**
	 * 
	 * @return Country name
	 */
	public String getCountry();

	/**
	 * 
	 * @return House reference number (city-wide)
	 */
	public int getReferenceNumber();

	/**
	 * Print address in post-format
	 * 
	 * @return
	 */
	public String print();
}
