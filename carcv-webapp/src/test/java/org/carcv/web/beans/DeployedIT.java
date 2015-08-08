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

package org.carcv.web.beans;

import org.carcv.web.test.AbstractIT;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

/**
 * Test if the app deploys and EJB CDI Injection works.
 */
@RunWith(Arquillian.class)
public class DeployedIT {

    @EJB
    private EntryBean entryBean;
    @EJB
    private RecognizerBean recognizerBean;
    @EJB
    private StorageBean storageBean;

    @Deployment
    public static WebArchive createDeployment() {
        return AbstractIT.createGenericDeployment();
    }

    @Test
    public void beanInjectionTest() {
        assertNotNull("Failed to inject EJB entryBean", entryBean);
        assertNotNull("Failed to inject EJB recognizerBean", recognizerBean);
        assertNotNull("Failed to inject EJB storageBean", storageBean);
    }
}
