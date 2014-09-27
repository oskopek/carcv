#!/usr/bin/augtool -LAsf

#<subsystem xmlns="urn:jboss:domain:datasources:2.0">
#    <datasources>
#        <datasource jndi-name="java:jboss/datasources/MySQLDS" pool-name="MySQLDS" enabled="true" use-java-context="true">
#            <connection-url>jdbc:mariadb://${env.MARIADB_}:3306/testdb?useUnicode=yes&amp;characterEncoding=UTF-8</connection-url>
#            <driver>mariadb</driver>
#            <security>
#                <user-name>root</user-name>
#                <password>mysqlPassword</password>
#            </security>
#        </datasource>
#        <drivers>
#            <driver name="mariadb" module="org.mariadb.jdbc">
#                <xa-datasource-class>org.mariadb.jdbc.MySQLDataSource</xa-datasource-class>
#           </driver>
#        </drivers>
#    </datasources>
#</subsystem>

set /augeas/load/Xml/lens Xml.lns
set /augeas/load/Xml/incl[2] /opt/wildfly/standalone/configuration/standalone.xml
load
defvar subsystem "/files/opt/wildfly/standalone/configuration/standalone.xml/server/profile/subsystem[#attribute/xmlns='urn:jboss:domain:datasources:2.0']"
set $subsystem/datasources/datasource[last()+1]/#attribute/jndi-name "java:jboss/datasources/MySQLDS"
defvar ds $subsystem/datasources/datasource[last()]
set $ds/#attribute/pool-name "MySQLDS"
set $ds/#attribute/enabled "true"
set $ds/#attribute/use-java-context "true"
set $ds/connection-url/#text "jdbc:mariadb://${env.MARIADB_PORT_3306_TCP_ADDR}:3306/testdb?useUnicode=yes&amp;characterEncoding=UTF-8"
set $ds/driver/#text "mariadb"
set $ds/security/user-name/#text "root"
set $ds/security/password/#text "mysqlPassword"
set $subsystem/datasources/drivers/driver[last()+1]/#attribute/name "mariadb"
defvar dr $subsystem/datasources/drivers/driver[last()]
set $dr/#attribute/module "org.mariadb.jdbc"
set $dr/xa-datasource-class/#text "org.mariadb.jdbc.MySQLDataSource"
save

print /augeas//error
