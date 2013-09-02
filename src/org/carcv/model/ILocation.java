/**
 * 
 */
package org.carcv.model;

/**
 * Interface of a Location on earth
 * @author oskopek
 *
 */
public interface ILocation {
	
	/**
	 * 
	 * @return Latitude coordinate of a given location in double precision
	 */
	public double latitude();
	
	/**
	 * 
	 * @return Longitude coordinate of a given location in double precision
	 */
	public double longitude();
	
}
