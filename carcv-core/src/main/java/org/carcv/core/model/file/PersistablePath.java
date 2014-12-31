/*
 * Copyright 2012 CarCV Development Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.carcv.core.model.file;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.carcv.core.model.AbstractEmbeddable;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * An internal persistable Path object storing the Path as a String. The {@link #getPath()} method returns a Path constructed
 * from the String.
 */
@Embeddable
class PersistablePath extends AbstractEmbeddable implements Comparable<PersistablePath> {

    private static final long serialVersionUID = -4725972338092448440L;

    private String pathStr;

    @SuppressWarnings("unused")
    private PersistablePath() {
    }

    /**
     * A constructor using String.
     *
     * @param path the path to set
     */
    public PersistablePath(String path) {
        this.pathStr = path;
    }

    /**
     * A constructor using Path.
     *
     * @param path Path to set
     */
    public PersistablePath(Path path) {
        this.pathStr = path.toString();
    }

    /*
     * (non-Javadoc)
     *
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

    /*
     * (non-Javadoc)
     *
     * @see org.carcv.core.model.AbstractModel#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(pathStr).toHashCode();
    }

    /**
     * Returns a Path constructed from {@link #getPathStr()} using {@link Paths#get(String, String...)}
     *
     * @return a Path constructed from the pathStr
     */
    @Transient
    public Path getPath() {
        return Paths.get(pathStr);
    }

    /**
     * Sets {@link #setPathStr(String)} using path.toString()
     *
     * @param path the path to set pathStr as
     */
    public void setPath(Path path) {
        setPathStr(path.toString());
    }

    /**
     * @return the pathStr
     */
    @NotNull
    private String getPathStr() {
        return pathStr;
    }

    /**
     * @param path the path to set
     */
    private void setPathStr(String path) {
        this.pathStr = path;
    }

    @Override
    public int compareTo(PersistablePath o) {
        return new CompareToBuilder().append(pathStr, o.pathStr).toComparison();
    }
}