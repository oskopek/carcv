#!/bin/bash

download_jboss () {
    root_dir=`pwd`
    cd $HOME
    rm -rf "$JBOSS_HOME"
    wget "$WF_LINK"
    tar xfz "wildfly-$WF_VERSION.tar.gz"
    cd "$root_dir"
}

if [ ! -d "$JBOSS_HOME" ]; then
    echo "JBoss dir doesn't exist, downloading wildfly! ( $JBOSS_HOME )"
    download_jboss
else
    echo "JBoss dir exists ( $JBOSS_HOME )"
    if [ `ls "$JBOSS_HOME" | wc -l` -eq 0 ]; then
        echo "... but is empty, downloading wildfly!"
        download_jboss
    fi
fi
cp carcv-webapp/src/test/resources/as_configuration/wildfly-standalone.xml $JBOSS_HOME/standalone/configuration/standalone.xml
