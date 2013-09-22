#!/bin/bash

filename=output
case=true
delimeter=---------

head -n 24 $filename
cat $filename | grep '$case'
tail -f $filename | grep '$case'
