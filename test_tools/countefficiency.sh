#!/bin/bash
basedir=$(pwd)
alldir=all
posdir=all/pos
negdir=all/neg
negcrit='neg*'
poscrit='pos*'

constpc='pos'
constnc='neg'

POSNOT=0 #positive but detected as negative
NEGNOT=0 #negative but detected as positive
POS=0 #positive and detected rightfully
NEG=0 #negative and detected rightfully
PTOTAL=0 #total number of pos
NTOTAL=0 #total number of neg

cd $posdir

for i in $poscrit
do

echo "POS: $i"
let POS++
done

for i in $negcrit
do

echo "NEGNOT: $i"
let NEGNOT++
done

cd $basedir
cd $negdir

for i in $poscrit
do

echo "POSNOT: $i"
let POSNOT++
done

for i in $negcrit
do

echo "NEG: $i"
let NEG++
done

cd $basedir

#OVERRIDE NTOTAL AND PTOTAL
cd $alldir
PTOTAL=$(cat list | grep "$poscrit" | wc -l)
NTOTAL=$(cat list | grep "$negcrit" | wc -l)
#

cd $basedir

echo
echo
echo
DASHES="---------------------------------"
DELIMETER="|---------------|---------------|"
echo $DASHES
echo -e "|VAR\t\t|VAL\t\t|"
echo $DASHES
echo -e "|POS:\t\t|\t$POS\t|"
echo -e "|POSNOT:\t|\t$POSNOT\t|"
echo -e "|NEG:\t\t|\t$NEG\t|"
echo -e "|NEGNOT:\t|\t$NEGNOT\t|"
echo $DELIMETER
echo -e "|PTOTAL:\t|\t$PTOTAL\t|"
echo -e "|NTOTAL:\t|\t$NTOTAL\t|"
echo $DASHES
echo
echo

POSEFF=$(bc -l <<< $POS/$PTOTAL)
NEGEFF=$(bc -l <<< $NEG/$NTOTAL)
DASHESLONG="-------------------------------------------------"

echo $DASHESLONG
echo -e "|VAR\t\t|EFF\t\t\t\t|"
echo $DASHESLONG
echo -e "|POSEFF:\t|\t$POSEFF\t|"
echo -e "|NEGEFF:\t|\t$NEGEFF\t|"
echo $DASHESLONG

echo
