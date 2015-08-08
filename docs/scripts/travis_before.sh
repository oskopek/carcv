#!/bin/bash

export ROOT_DIR=`pwd`
cd $HOME
if [ ! -d "$JBOSS_HOME" ]; then
    wget "$WF_LINK"
    tar xfz "wildfly-$WF_VERSION.tar.gz"
fi
cd $ROOT_DIR
cp $ROOT_DIR/carcv-webapp/src/test/resources/as_configuration/wildfly-standalone.xml $JBOSS_HOME/standalone/configuration/standalone.xml
