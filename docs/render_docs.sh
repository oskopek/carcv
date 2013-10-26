#!/bin/bash

basedir=`pwd`

echo "Current directory:" ${basedir}

#Remove old versions

cd ${basedir}

rm -rf html/
mkdir html
mkdir html/core
mkdir html/cpp
mkdir html/webapp

#Root dir

cd ${basedir}

asciidoctor *.adoc
mv *.html ${basedir}/html/

#Core dir

cd ${basedir}/core

asciidoctor *.adoc
mv *.html ${basedir}/html/core/

#CPP dir

cd ${basedir}/cpp

asciidoctor *.adoc
mv *.html ${basedir}/html/cpp/

#WebApp dir

cd ${basedir}/webapp

asciidoctor *.adoc
mv *.html ${basedir}/html/webapp/
