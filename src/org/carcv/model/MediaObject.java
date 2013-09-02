/**
 * 
 */
package org.carcv.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Used for persistence of remote and local files in a database
 * TODO: add local caching of files
 * 
 * @author oskopek
 *
 */
@Entity
public class MediaObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private long id;

	@Column(name="URL")
	private String URL;
	
	@Column(name="mediaType")
	private MediaType mediaType;
	
	@SuppressWarnings("unused")
	private MediaObject() {
		//hibernate empty
	}
	
	public MediaObject(String URL, MediaType mediaType) {
		this.URL = URL;
		this.mediaType = mediaType;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the URL
	 */
	public String getURL() {
		return URL;
	}

	/**
	 * @param URL the URL to set
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
	 * @param mediaType the mediaType to set
	 */
	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MediaObject [id=" + id + ", URL=" + URL + ", mediaType=" + mediaType + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(URL).append(mediaType).toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof MediaObject) {
			MediaObject obj = (MediaObject) o;
			return new EqualsBuilder().append(URL, obj.URL).append(mediaType, obj.mediaType).isEquals();
		}
		return false;
	}
	
}
