# -*- mode: ruby -*-
# vi: set ft=ruby :

# Vagrantfile API/syntax version. Don't touch unless you know what you're doing!
VAGRANTFILE_API_VERSION = "2"

Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|
  config.vm.box     = "trusty64"
  config.vm.box_url = "https://cloud-images.ubuntu.com/vagrant/trusty/current/trusty-server-cloudimg-amd64-vagrant-disk1.box"
  #config.vm.box_url = "trusty-server-cloudimg-amd64-vagrant-disk1.box"
  config.vm.network :forwarded_port, :guest => 9443, :host => 9443
  config.vm.provision :shell, path: "install.sh"
  config.vm.synced_folder "repositorio/", "/home/vagrant"
  
  config.vm.provider "virtualbox" do |v|
	v.memory = 3000
	v.cpus = 2
  end
end