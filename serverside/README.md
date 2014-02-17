# Serverside - NodeJS Express Application

NOTE: this is an replacable layer. It was added, configured and developed for demonstration and persistent data. 

## Vagrant vm 

This folder is intended to be used as vagrants synced folder. But it`s not a must! You can ignore vagrant files and use it as you like (do not put them into gitignore btw ;P). 

In the Vagrantfile everything is setup and configured to be used as virtual box dev environment. Use following commands to get up and running (terminal/cmd):
        
        vagrant up

This loads the Vagrantfile configuration and will download all software needed by the vm. In case of first start it will download the debian vm. 

After starting you can use the VM with

        vagrant ssh

to login to your box. 

### First time setup

After first boot and ssh-ing into the box, you need some additional software to run serverside-stack. 

        cd /vagrant
        chmod +x install.sh
        ./install.sh

This will install mysql, ffmpeg and nodejs. 

## Usage and setup serverside-application

* open terminal here
* type 

		npm install

* after installation of all required packages: 

		node app.js