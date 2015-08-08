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

package org.carcv.core.model;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * An abstraction of a number plate.
 * <p>
 * The text is the actual text printed on the number plate.
 * The origin represents the country in which the number plate was issued.
 */
@Embeddable
public class NumberPlate extends AbstractEmbeddable implements Comparable<NumberPlate> {

    private static final long serialVersionUID = -2507938473851975932L;

    private String text;

    private String origin;

    @SuppressWarnings("unused")
    private NumberPlate() {
        // hibernate stub
    }

    /**
     * Constructs an initialized NumberPlate object.
     *
     * @param text the text to set
     * @param origin the origin to set
     */
    public NumberPlate(String text, String origin) {
        this.text = text;
        this.origin = origin;
    }

    /**
     * Constructs an initialized NumberPlate object.
     * <p>
     * The origin is set to an empty String.
     *
     * @param text the text to set
     */
    public NumberPlate(String text) {
        this.text = text;
        this.origin = "";
    }

    /**
     * Text is the main part of the number plate, the actual String depicted on it.
     *
     * @return the text
     */
    @NotNull
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Origin of the plate is a two character long String derived from the NumberPlate text syntax - the issuer.
     *
     * @return the origin
     */
    @NotNull
    public String getOrigin() {
        return origin;
    }

    /**
     * @param origin the origin to set
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    @Override
    public String toString() {
        return "NumberPlate [text=" + getText() + ", origin=" + getOrigin() + "]";
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getText()).append(getOrigin()).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof NumberPlate)) {
            return false;
        }
        NumberPlate other = (NumberPlate) obj;
        return new EqualsBuilder().append(getText(), other.getText()).append(getOrigin(), other.getOrigin()).isEquals();
    }

    @Override
    public int compareTo(NumberPlate o) {
        return new CompareToBuilder().append(text, o.text).append(origin, o.origin).toComparison();
    }
}
