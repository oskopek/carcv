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

/**
 * An abstraction of an entry object, which is a virtual complex of CarData and corresponding list of video frames (images).
 */
public abstract class AbstractEntry extends AbstractModel {

    private static final long serialVersionUID = 2309289364991527040L;

    /**
     * @return the CarData object corresponding to the Entry
     */
    public abstract CarData getCarData();

    /**
     * @return the list of CarImages corresponding to the Entry
     */
    public abstract List<? extends AbstractCarImage> getCarImages();

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);
}