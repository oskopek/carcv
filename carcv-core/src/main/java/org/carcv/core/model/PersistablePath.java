/**
 * 
 */
package org.carcv.core.model;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author oskopek
 * 
 */
@Embeddable
public class PersistablePath extends AbstractEmbeddable implements Comparable<PersistablePath> {

    /**
     * 
     */
    private static final long serialVersionUID = -4725972338092448440L;

    private String pathStr;

    @SuppressWarnings("unused")
    private PersistablePath() {
    }

    public PersistablePath(String path) {
        this.pathStr = path;
    }

    public PersistablePath(Path path) {
        this.pathStr = path.toString();
    }

    /* (non-Javadoc)
     * @see org.carcv.core.model.AbstractModel#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PersistablePath)) {
            return false;
        }
        PersistablePath other = (PersistablePath) o;

        return new EqualsBuilder().append(this.pathStr, other.pathStr).isEquals();
    }

    /* (non-Javadoc)
     * @see org.carcv.core.model.AbstractModel#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(pathStr).toHashCode();
    }

    /**
     * @return the path
     */
    @Transient
    public Path getPath() {
        return Paths.get(pathStr);
    }

    /**
     * @param path
     *            the path to set
     */
    public void setPath(Path path) {
        this.pathStr = path.toString();
    }

    /**
     * @return the pathStr
     */
    @NotNull
    public String getPathStr() {
        return pathStr;
    }

    /**
     * @param pathStr
     *            the pathStr to set
     */
    public void setPathStr(String path) {
        this.pathStr = path;
    }

    @Override
    public int compareTo(PersistablePath o) {
        return new CompareToBuilder().append(pathStr, o.pathStr).toComparison();
    }

}
