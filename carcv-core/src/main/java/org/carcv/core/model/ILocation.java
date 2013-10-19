/**
 * 
 */
package org.carcv.core.model;

/**
 * Interface of a Location on earth
 * 
 * @author oskopek
 * 
 */
public interface ILocation {

	/**
	 * 
	 * @return Latitude coordinate of a given location in double precision
	 */
	public double getLatitude();

	/**
	 * 
	 * @return Longitude coordinate of a given location in double precision
	 */
	public double getLongitude();

}
