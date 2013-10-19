/**
 * 
 */
package org.carcv.impl.core.model;

import javax.persistence.Entity;

import org.carcv.core.model.AbstractMediaObject;
import org.carcv.core.model.MediaType;

/**
 * Used for persistence of remote and local files in a database TODO: add local caching of files
 * 
 * @author oskopek
 * 
 */
@Entity
public class MediaObject extends AbstractMediaObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3002993447000570366L;
	
	private String URL;

	private MediaType mediaType;

	/**
	 * For EJB
	 */
	public MediaObject() {
		// hibernate empty
	}

	/**
	 * 
	 * @param URL
	 * @param mediaType
	 */
	public MediaObject(String URL, MediaType mediaType) {
		this.URL = URL;
		this.mediaType = mediaType;
	}

	/**
	 * @return the URL
	 */
	public String getURL() {
		return URL;
	}

	/**
	 * @param URL
	 *            the URL to set
	 */
	public void setURL(String URL) {
		this.URL = URL;
	}

	/**
	 * @return the mediaType
	 */
	public MediaType getMediaType() {
		return mediaType;
	}

	/**
	 * @param mediaType
	 *            the mediaType to set
	 */
	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	/*
	@Override
	public String toString() {
		return "MediaObject [id=" + id + ", URL=" + URL + ", mediaType="
				+ mediaType + "]";
	}
	*/

}
