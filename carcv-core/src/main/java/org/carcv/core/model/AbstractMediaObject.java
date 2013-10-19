/**
 * 
 */
package org.carcv.core.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.carcv.impl.core.model.MediaObject;

/**
 * @author oskopek
 *
 */
@Entity
//@Table(name = "mediaobject")
public abstract class AbstractMediaObject extends AbstractModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    

    //@Column(name = "URL")
    @NotNull
    public abstract String getURL();

    //@Column(name = "mediatype")
    @NotNull
    public abstract MediaType getMediaType();
    


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
