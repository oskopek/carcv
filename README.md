# CarCV - Car recognizing and speed calculating platform

Copyright (C) 2012-2013, Ondrej Skopek. All rights reserved.

All licensing issues are addressed in file LICENCE.

Configuring the project with the m2eclipse plugin
-------------------------------------------------

The m2eclipse plugin is a plugin in Eclipse for Maven.
This is the new way (and compatible with tycho).

* Open Eclipse

* Follow [the installation instructions of m2eclipse](http://m2eclipse.sonatype.org/).

    * Follow the link *Installing m2eclipse* at the bottom.

* Click menu *File*, menu item *Import*, tree item *Maven*, tree item *Existing Maven Projects*.

* Click button *Browse*, select a repository directory. For example `~/git/carcv`.

For more information, see [the m2eclipse book](http://www.sonatype.com/books/m2eclipse-book/reference/)

Configuring the project with the deprecated maven-eclipse-plugin
----------------------------------------------------------------

> NOTE: This way of configuring the project is old and deprecated.
> Please read the section `Configuring the project with the m2eclipse plugin` above.

The maven-eclipse-plugin plugin is a plugin in Maven for Eclipse.
This is the old way (of which the development has stopped).

Run this command to generate `.project` and `.classpath` files:

    $ mvn eclipse:eclipse

* Open Eclipse

* Menu item *Import existing projects*, navigate to the project base directory, select all the projects (= modules) it lists.

Important note: `mvn eclipse:eclipse` does not work for our eclipse plugins because it is not compatible with tycho
(and never will be).

Building
--------

To *build*, run: mvn clean package (optional: -DskipTests)

To *clean*, run: mvn clean

Integration Tests
-----------------

In `standalone.xml`

To run integration tests successfully make sure `ExampleDS` datasource is configured correctly
and add the following to `security-domains`:
```
    <security-domain name="carcv_gui" cache-type="default">
        <authentication>
            <login-module code="UsersRoles" flag="required">
                <module-option name="usersProperties" value="users.properties" />
                <module-option name="rolesProperties" value="roles.properties" />
            </login-module>
        </authentication>
    </security-domain>
```
