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

package org.carcv.web.test;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class RolesIT {

    @Inject
    public HttpServletRequest adminRequest;

    @Inject
    public HttpServletRequest userRequest;

    private static final String adminRole = "admin";
    private static final String userRole = "user";

    @Deployment
    public static WebArchive createDeployment() {
        return AbstractIT.createGenericDeployment();
    }

    @Test
    public void userRoleTest() throws Exception {
        userRequest.login("carcv_user", "user_test");
        assertTrue(userRequest.isUserInRole(userRole));
        userRequest.logout();
    }

    @Test
    public void adminRoleTest() throws Exception {
        adminRequest.login("carcv_admin", "admin_test");
        assertTrue(adminRequest.isUserInRole(adminRole));
        adminRequest.logout();
    }

}
