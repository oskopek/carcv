/*
 * Copyright 2012-2014 CarCV Development Team
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

import java.awt.image.BufferedImage;

/**
 * An abstraction for a video frame (image). All implementations need to handle the loading/saving (best on-demand) of the image
 * from the chosen data source.
 */
public abstract class AbstractCarImage extends AbstractModel implements AutoCloseable {

    private static final long serialVersionUID = 6868694395465415996L;

    /**
     * @return the image
     */
    public abstract BufferedImage getImage();
}