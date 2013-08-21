#!/bin/bash
DASHES="------------------------------------------------------"
basedir=$(pwd)


echo $DASHES
echo "Build script by odenkos"
echo $DASHES
echo
echo "WARNING: Will recursively erase any \"build\" dir in directory: ${PWD}"
echo
echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
read -p "!!!ARE YOU SURE YOU WANT TO DO THIS? [Y/N]: " ans_yn
case "$ans_yn" in
    [Yy]|[Yy][Ee][Ss]) echo "Removing \"${PWD}/build\" ...";;

    *) exit 3;;
esac

echo
echo
echo
echo $DASHES
echo "-------REMOVING BUILD DIR"
echo $DASHES
cd $basedir
rm -rfv  build/

echo
echo
echo
echo $DASHES
echo "-------CREATING BUILD DIR"
echo $DASHES
cd $basedir
mkdir -v build

echo
echo
echo
echo $DASHES
echo "-------RUNNING CMAKE"
echo $DASHES
cd $basedir
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
#cp -v $basedir/build/src/carcv $basedir/test/all/
#cp -v $basedir/build/cardetect/carcv $HOME/tests/post6/

echo
echo
echo
echo $DASHES
echo "-------FINISHED"
echo $DASHES
