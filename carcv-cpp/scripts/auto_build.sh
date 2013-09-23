#!/bin/bash
DASHES="------------------------------------------------------"
build_basedir=$(pwd)


echo $DASHES
echo "AutoBuild script by oskopek"
echo $DASHES
echo

echo
echo
echo
echo $DASHES
echo "-------CREATING BUILD DIR"
echo $DASHES
cd $build_basedir
mkdir -v build

echo
echo
echo
echo $DASHES
echo "-------RUNNING CMAKE"
echo $DASHES
cd $build_basedir
cd build
cmake .. -DCMAKE_INSTALL_PREFIX=./install

echo
echo
echo
echo $DASHES
echo "-------RUNNING MAKE"
echo $DASHES
make

echo
echo
echo
echo $DASHES
echo "-------RUNNING MAKE INSTALL"
echo $DASHES
make install

#echo
#echo
#echo
#echo $DASHES
#echo "------DISTRIBUTING"               ###WARNING if you want to distribute binaries to test places, do so here
#echo $DASHES
#cp -v $build_basedir/build/install/bin/carcv $build_basedir/test/
#cp -v $build_basedir/build/install/bin/ocr_recognizer $build_basedir/test/
#cp -v $build_basedir/build/install/bin/ocr_train $build_basedir/test/

echo
echo
echo
echo $DASHES
echo "-------FINISHED"
echo $DASHES
