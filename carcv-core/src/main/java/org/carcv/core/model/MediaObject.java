/**
 * 
 */
package org.carcv.core.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Used for persistence of remote and local files in a database TODO: add local caching of files
 * 
 * @author oskopek
 * 
 */
@Entity
public class MediaObject extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3002993447000570366L;
	
	private String URL;

	private MediaType mediaType;


	@SuppressWarnings("unused")
    private MediaObject() {
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
	@NotNull
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
	@NotNull
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
	
	 /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getURL()).append(getMediaType()).toHashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof MediaObject) {
            MediaObject obj = (MediaObject) o;
            return new EqualsBuilder().append(getURL(), obj.getURL())
                    .append(getMediaType(), obj.getMediaType()).isEquals();
        }
        return false;
    }

}
