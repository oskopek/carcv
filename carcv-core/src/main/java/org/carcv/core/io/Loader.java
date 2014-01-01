/*
 * Copyright 2013 CarCV Development Team
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

package org.carcv.core.io;

import java.io.IOException;
import java.util.List;

import org.carcv.core.model.AbstractEntry;

/**
 * An interface for any object that wants to load a batch (java.util.List) of {@link AbstractEntry}-s. The input data source and
 * method are responsibilities of the implementation.
 * <p>
 * If you implement this interface, it is considered good conduct to implement a complementing {@link Saver} interface and link
 * here) too. The saving/loading shouldn't in any way lose data contained in the <code>AbstractEntry</code> -s. You are also
 * encouraged to add other, more specific saving methods.
 */
public interface Loader {

    /**
     * Loads all batches according to the implementation details.
     *
     * @throws IOException if an error during the save occurs
     * @return a list of AbstractEntry-s that were loaded from the data source
     */
    public List<? extends AbstractEntry> loadAll() throws IOException;

    /**
     * Loads all batches since last load of new batches according to the implementation details.
     *
     * @throws IOException if an error during the save occurs
     * @return a list of AbstractEntry-s that were loaded from the data source
     */
    public List<? extends AbstractEntry> loadNew() throws IOException;
}