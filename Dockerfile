FROM jboss/wildfly
ADD docker/org /opt/wildfly/modules/org/
ADD deployments/ROOT.war /opt/wildfly/standalone/deployments/
ADD docker/add-datasource.sh /opt/wildfly/bin/
RUN /opt/wildfly/bin/add-datasource.sh