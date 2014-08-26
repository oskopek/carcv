/*
 * Copyright 2014 CarCV Development Team
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
package org.carcv.impl.core.util;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;

import java.util.List;
import java.util.NoSuchElementException;

public class CollectionUtils {

    public static <T> T highestCountElement(final List<T> list) throws NoSuchElementException {
        final Multiset<T> plateSet = HashMultiset.create();
        plateSet.addAll(list);
        return Multisets.copyHighestCountFirst(plateSet).iterator().next();
    }
}
