#!/usr/bin/augtool -LAsf

#<security-domain name="carcv" cache-type="default">
#    <authentication>
#        <login-module code="UsersRoles" flag="required">
#            <module-option name="usersProperties" value="users.properties" />
#            <module-option name="rolesProperties" value="roles.properties" />
#        </login-module>
#    </authentication>
#</security-domain>

set /augeas/load/Xml/lens Xml.lns
set /augeas/load/Xml/incl[2] /opt/wildfly/standalone/configuration/standalone.xml
load
defvar subsystem "/files/opt/wildfly/standalone/configuration/standalone.xml/server/profile/subsystem[#attribute/xmlns='urn:jboss:domain:security:1.2']"
set $subsystem/security-domains/security-domain[last()+1]/#attribute/name "carcv"
defvar sd $subsystem/security-domains/security-domain[last()]
set $sd/authentication/login-module/#attribute/code "UsersRoles"
set $sd/authentication/login-module/#attribute/flag "required"
set $sd/authentication/login-module/module-option[last()+1]/#attribute/name "usersProperties"
set $sd/authentication/login-module/module-option[last()]/#attribute/value "users.properties"
set $sd/authentication/login-module/module-option[last()+1]/#attribute/name "rolesProperties"
set $sd/authentication/login-module/module-option[last()]/#attribute/value "roles.properties"
save

print /augeas//error
