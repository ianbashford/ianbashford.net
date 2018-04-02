+++
date = "2018-04-02T18:24:33Z"
title = "dnscrypt-proxy, running in a u-root initramfs, xen vm"
draft = "false"

+++
### Setup Docker build area
```sh
FROM base/archlinux

RUN pacman -Sy && \
    pacman -S go \
    sudo \
    vim \
    ca-certificates \
    libcap \
    git --noconfirm

RUN useradd -m -G wheel -s /bin/bash build && \
    echo "%wheel ALL=(ALL) NOPASSWD: ALL" >> /etc/sudoers

USER build
CMD ["/bin/bash"]
```
Build and run...
```sh
docker build -t uroot .
docker run --rm -it --name uroot -v <somefolder>:/home/build
```
We'll assume that in <somefolder> you've pulled the built image of dnscrypt-proxy from https://github.com/jedisct1/dnscrypt-proxy/releases 
Now install u-root
```sh
go get github.com/u-root/u-root
cd go/bin
```
Then run a script that looks like this:
```sh
./u-root -build=bb \
	-files "$HOME/dnscrypt-proxy/dnscrypt-proxy:inito" \
	-files "$HOME/dnscrypt-proxy/dnscrypt-proxy.toml:dnscrypt-proxy.toml" \
	-files "$HOME/dnscrypt-proxy/public-resolvers.md:public-resolvers.md" \
	-files "$HOME/dnscrypt-proxy/public-resolvers.md.minisig:public-resolvers.md.minisig" \
	-files "/etc/ca-certificates/extracted/tls-ca-bundle.pem:etc/ssl/certs/ca-certicates.crt" 
```
NOTES: dnscrypt-proxy makes https calls, so it needs access to ca-certificates in a well known path. 
NOTES: cheating here by copying it all into root folder, and renaming the binary 'inito' so it's automatically run -could be neater
That generates a file initramfs.linux_amd64.cpio in /tmp  (you can redirect that to some other file).  
To run in xen, grab your kernel from /boot, and create a xen config file - this is for an Alpine PV host... vi uroot.cfg
```sh
# Kernel paths for install
kernel = "<blah>/uroot/vmlinuz-virthardened"
ramdisk = "<blah>/uroot/initramfs.linux_amd64.cpio"
extra="modules=loop,squashfs console=hvc0 ip=dhcp"

# Network configuration
vif = ['bridge=xenbr0']

# DomU settings
memory = 128
name = "uroot-a1"
vcpus = 1
maxvcpus = 1
```

Then run with
```sh
sudo xl create -f ./uroot.cfg -c
```

