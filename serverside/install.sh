# install.sh
# always install latest stuff 


sudo apt-get update 

sudo apt-get install mysql-server ffmpeg

sudo apt-get install python g++ make checkinstall
src=$(mktemp -d) && cd $src
wget -N http://nodejs.org/dist/node-latest.tar.gz
tar xzvf node-latest.tar.gz && cd node-v*
./configure
sudo checkinstall -y --install=no --pkgversion 0.10.25  make -j$(($(nproc)+1)) install  # Replace with current version number.
sudo dpkg -i node_*

# TODO run db init script