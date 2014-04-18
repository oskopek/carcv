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

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CollectionUtilsTest {

    private List<String> list;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        list = new ArrayList<>();
    }

    @After
    public void tearDown() {
        list.clear();
    }

    @Test
    public void testEmptyHighestCountElement() {
        thrown.expect(NoSuchElementException.class);
        assertNotNull(CollectionUtils.highestCountElement(list));
    }

    @Test
    public void testEqualHighestCountElement() {
        list.add("T1");
        list.add("T2");
        list.add("T3");
        assertEquals("T1", CollectionUtils.highestCountElement(list));
    }

    @Test
    public void testNonEqualHighestCountElement() {
        list.add("T1");
        list.add("T2");
        list.add("T3");
        list.add("T2");
        assertEquals("T2", CollectionUtils.highestCountElement(list));
    }
}
