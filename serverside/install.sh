#!/bin/sh
# install.sh
# always install latest stuff 

echo "Initialisation started. I will get actual packages list."
apt-get update

echo "Installing mysql-server and libav-tools"

apt-get install mysql-server libav-tools

echo "\nNow installing nodejs\n"

apt-get install python g++ make checkinstall
src=$(mktemp -d) && cd $src
wget -N http://nodejs.org/dist/node-latest.tar.gz
tar xzvf node-latest.tar.gz && cd node-v*
./configure
sudo checkinstall -y --install=no  make -j$(($(nproc)+1)) install  # Replace with current version number.
sudo dpkg -i node_*

echo "setting up db data..."

cp /vagrant/my.cnf /etc/mysql/my.cnf

sudo mysql --user=root --password=mmk2014 < /vagrant/init.sql
sudo mysql --user=root --password=mmk2014 < /vagrant/hipsterbility_test_data.sql

echo "installing middleware packages."

cd /vagrant && npm install

echo "fin. Happiness everywhere."