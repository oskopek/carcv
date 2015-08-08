#!/bin/bash

export ROOT_DIR=`pwd`
cd $HOME
if [ ! -d "$JBOSS_HOME" ]; then
    wget http://download.jboss.org/jbossas/7.1/jboss-as-$JBOSSAS/jboss-as-$JBOSSAS.tar.gz
    tar xfz jboss-as-$JBOSSAS.tar.gz
fi
cd $ROOT_DIR
cp $ROOT_DIR/carcv-webapp/src/test/resources/as_configuration/jboss-as-standalone.xml $JBOSS_HOME/standalone/configuration/standalone.xml
