# install.sh
# always install latest stuff 

echo "Initialisation started. I will get actual packages list."
sudo apt-get update 

echo "Installing mysql-server and ffmpeg"

sudo apt-get install mysql-server ffmpeg

echo "Now installing nodejs"

sudo apt-get install python g++ make checkinstall
src=$(mktemp -d) && cd $src
wget -N http://nodejs.org/dist/node-latest.tar.gz
tar xzvf node-latest.tar.gz && cd node-v*
./configure
sudo checkinstall -y --install=no --pkgversion 0.10.25  make -j$(($(nproc)+1)) install  # Replace with current version number.
sudo dpkg -i node_*

echo "setting up db data..."

sudo cp /vagrant/my.cnf /etc/mysql/my.cnf

sudo mysql --user=root --password=mmk2014 < /vagrant/init.sql

echo "installing middleware packages."

npm install

echo "fin. Happiness everywhere."