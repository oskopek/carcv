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
echo "-------COPYING SOURCE FILES OVER"
echo $DASHES
cd $basedir
cd cardetect
rm -rfv carmatcher* carrectangles*
cp -v /home/odenkos/c_workspace/CarProject/cardetect/* .

echo
echo
echo
echo $DASHES
echo "-------RUNNING CMAKE"
echo $DASHES
cd $basedir
cd build
cmake ..

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
echo "------DISTRIBUTING"
echo $DASHES
cp -v $basedir/build/cardetect/carrect $basedir/test/all/

echo
echo
echo
echo $DASHES
echo "-------FINISHED"
echo $DASHES
