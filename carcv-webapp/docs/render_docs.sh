#!/bin/bash

rm html/*
asciidoctor *
mv *.html html/
