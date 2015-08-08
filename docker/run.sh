#!/bin/bash
sudo docker pull oskopek/carcv-webapp
sudo docker run -d --name=mariadb fedora/mariadb
sudo docker run -it --rm --link mariadb:mariadb -p 8080:8080 oskopek/carcv-webapp
