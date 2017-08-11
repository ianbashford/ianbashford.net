+++
date = "2017-08-11T09:24:33Z"
title = "Arch Install - UEFI Boot with BTRFS root filesystem"
draft = "false"

+++

## Disk Formatting
Three partitions for this example - EFI, swap and then a single BTRFS for everything else

One consequence is that you can't snapshot /boot (as it's formatted to FAT32)... 




```bash
Create GPT Partition Table disk
EFI partition, FAT32 format, 512M (will mount on /boot ) e.g. /dev/sda1
swap e.g. /dev/sda2
BTRFS filesystem for root,var,home etc e.g. /dev/sda3
```
## Install Arch
*mkswapfs / swapon etc*
```bash
mount /dev/sda3 /mnt
btrfs subvolume create /mnt/@root
btrfs subvolume create /mnt/@var
btrfs subvolume create /mnt/@snapshots
umount /mnt
mount -o noatime,compress=lzo,space_cache,subvol=@root /dev/sda3 /mnt
mkdir /mnt/{boot,var,.snapshots}
mount -o noatime,compress=lzo,space_cache,subvol=@var /dev/sda3 /mnt/var
mount -o noatime,compress=lzo,space_cache,subvol=@snapshots /dev/sda3 /mnt/.snapshots
mount /dev/sda1 /mnt/boot
pacstrap /mnt base
genfstab -U /mnt >> /mnt/etc/fstab
arch-chroot /mnt
```
*then setup /etc/locale.gen locale-gen /etc/locale.conf hostname*

## UEFI Boot setup
/boot will hold the EFI boot files.  This makes it easy to reference the kernel in the refind config, and keep it updated with no extra effort 

refind.conf requires a manual entry, as we need to specify the root BTRFS subvolume


```bash
pacman -S refind-efi
cp /usr/share/refind/refind_x64.efi /boot/EFI/Boot/bootx64.efi
cp -r /usr/share/refind/drivers_x64/ /boot/EFI/Boot/
```
*/boot/EFI/Boot/refind.conf*
```bash
scanfor manual
timeout 1 # -1 for immediate
menuentry "Blah" {
  loader vmlinux-linux
  initrd initramfs-linux.img
  options "root=PARTUUID=XX rw rootflags=subvol=@root add_efi_memmap"
}
```

`exit`
