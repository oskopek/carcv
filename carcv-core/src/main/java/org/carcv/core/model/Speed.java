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

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * An abstraction of the speed a car is moving at, along with it's unit.
 */
@Embeddable
public class Speed extends AbstractEmbeddable implements Comparable<Speed> {

    private static final long serialVersionUID = 1816208535143255888L;

    private Double speed;
    private SpeedUnit unit;

    @SuppressWarnings("unused")
    private Speed() {
        // persistence constructor stub
    }

    /**
     * Constructs an initialized Speed object.
     * <p>
     * Initializes with the default unit - {@link SpeedUnit#KPH}
     *
     * @param speed the speed to set
     */
    public Speed(Double speed) {
        this.speed = speed;
        this.unit = SpeedUnit.KPH;
    }

    /**
     * Constructs an initialized Speed object.
     *
     * @param speed the speed to set
     * @param unit the unit to set
     */
    public Speed(Double speed, SpeedUnit unit) {
        this.speed = speed;
        this.unit = unit;
    }

    /**
     * @return the speed
     */
    @NotNull
    // @Range(min = 0, message = "Speed is less or equal to 0!")
    public Double getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * A value from {@link SpeedUnit}.
     *
     * @see SpeedUnit
     * @return the unit
     */
    @NotNull
    public SpeedUnit getUnit() {
        return unit;
    }

    /**
     * Set a value from {@link SpeedUnit}.
     *
     * @see SpeedUnit
     * @param unit the unit to set
     */
    public void setUnit(SpeedUnit unit) {
        this.unit = unit;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Speed [speed=" + getSpeed().toString() + ", unit=" + getUnit().toString() + "]";
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getSpeed()).append(getUnit()).toHashCode();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Speed)) {
            return false;
        }
        Speed other = (Speed) obj;
        return new EqualsBuilder().append(this.getSpeed(), other.getSpeed()).append(this.getUnit(), other.getUnit())
            .isEquals();
    }

    @Override
    public int compareTo(Speed o) {
        return new CompareToBuilder().append(speed, o.speed).append(unit, o.unit).toComparison();
    }
}