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

import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 
 */
public abstract class AbstractEntry extends AbstractModel {

    /**
     * 
     */
    private static final long serialVersionUID = 2309289364991527040L;

    /*
    private CarData carData;
    private AbstractCarImage carImage;
    
    public AbstractEntry(CarData carData, AbstractCarImage carImage) {
        this.carData = carData;
        this.carImage = carImage;
    }

    */

    public abstract CarData getCarData();

    public abstract List<? extends AbstractCarImage> getCarImages();

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getCarData()).append(getCarImages().size()).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AbstractEntry)) {
            return false;
        }
        AbstractEntry other = (AbstractEntry) obj;

        return new EqualsBuilder().append(getCarImages().size(), other.getCarImages().size())
                .append(getCarData(), other.getCarData()).isEquals();

    }

}