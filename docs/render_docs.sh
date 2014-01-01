#!/bin/bash

basedir=`pwd`

echo "Current directory:" ${basedir}

#Remove old versions

cd ${basedir}

rm -rf html/
mkdir html

if [ -d "${basedir}/core" ]; then
mkdir html/core
fi

if [ -d "${basedir}/cpp" ]; then
mkdir html/cpp
fi

if [ -d "${basedir}/webapp" ]; then
mkdir html/webapp
fi

#Root dir

cd ${basedir}

asciidoctor *.adoc
mv *.html ${basedir}/html/

#Core dir

if [ -d "${basedir}/core" ]; then

cd ${basedir}/core

asciidoctor *.adoc
mv *.html ${basedir}/html/core/

fi

#CPP dir

if [ -d "${basedir}/cpp" ]; then

cd ${basedir}/cpp

asciidoctor *.adoc
mv *.html ${basedir}/html/cpp/

fi

#WebApp dir

if [ -d "${basedir}/webapp" ]; then

cd ${basedir}/webapp

asciidoctor *.adoc
mv *.html ${basedir}/html/webapp/

fi

#docs directory
if [ -d "${basedir}/docs" ]; then

cd ${basedir}/docs
./render_docs.sh
fi