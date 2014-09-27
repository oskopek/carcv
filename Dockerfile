FROM jboss/wildfly

MAINTAINER Ondrej Skopek "https://github.com/oskopek"

ADD docker/org /opt/wildfly/modules/org/
ADD deployments/ROOT.war /opt/wildfly/standalone/deployments/

ADD docker/add-security-domain.sh /opt/wildfly/bin/
ADD docker/add-mysql-datasource.sh /opt/wildfly/bin/

RUN /opt/wildfly/bin/add-mysql-datasource.sh
RUN /opt/wildfly/bin/add-security-domain.sh

EXPOSE 8080