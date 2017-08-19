+++
date = "2017-08-19T11:24:33Z"
title = "Arch Linux - rootfs in ram / boot to ram"
draft = "false"

+++


## Arch Linux - rootfs in ram / boot to ram

Arch comes with some simple scripts for booting root fs into ram.
 [liveroot](https://aur.archlinux.org/packages/liveroot/) takes only some simple configuration

First, install the package
```bash
yaourt -S liveroot
```
This puts the required hooks into /usr/lib/initcpio

An example mkinitcpio.conf is placed into /usr/share/liveroot
I modified the MODULES line as follows
```bash
MODULES="zram ext2 btrfs overlay"
```
And to the HOOKS line I added encrypt and oroot - mine ends up looking like this
```bash
HOOKS="base udev encrypt oroot autodetect modconf block filesystems keyboard fsck"
```
Finally, I added the oroot directive to the kernel options - mine are in refind.conf - I made this an additional option so I can boot normally for updates / adding software etc.
```bash
menuentry "Arch Linux SSD to Ram" {
  loader vmlinuz-linux
  initrd initramfs-linux.img
  options "root=PARTUUID=0bac1068-0c23-4563-835d-7ada1e0ad1f0 oroot=compressed rw rootflags=subvol=@root add_efi_memmap"
}
```

To make this work nicely, /home is still mounted onto the disk and you could try mounting /var the same way.

