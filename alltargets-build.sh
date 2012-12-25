#!/bin/bash
DASHES="------------------------------------------------------"
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
rm -rfv  build/

echo
echo
echo
echo $DASHES
echo "-------CREATING BUILD DIR"
echo $DASHES
mkdir build
cd build

echo
echo
echo
echo $DASHES
echo "-------RUNNING CMAKE"
echo $DASHES
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
echo "-------FINISHED"
echo $DASHES
