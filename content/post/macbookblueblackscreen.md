---
title: "Macbookblueblackscreen"
date: 2020-02-15T16:50:01Z
draft: false
---
Symptom: Macbook (2011) won't boot.  Blue screen with lines instead of black.  Stuck in a fail to boot loop

Solution (tested High Sierra Update 6)

Boot into Single User Mode - CMD-S held down on boot.  When you see the terminal, hit enter a couple of times to get the prompt.  Then run:
```
sudo nvram fa4ce28d-b62f-4c99-9cc3-6815686e30f9:gpu-power-prefs=%01%00%00%00
reboot
```

Boot into Recovery Mode - CMD-R held down on boot.  Choose the right language, then under the Utilities menu, start a terminal and type:
```
csrutil disable
reboot
```

Back to Single User Mode - CMD-S held down on boot.  When you see the terminal, hit enter a couple of times to get the prompt.  Then run:
```
fsck -fy
mount -uw /
sudo mkdir /amd-backup-kexts/
sudo mv /System/Library/Extensions/AMD*.* /amd-backup-kexts/
sudo rm -rf /System/Library/Caches/com.apple.kext.caches/
sudo mkdir /System/Library/Caches/com.apple.kext.caches/
sudo touch /System/Library/Extensions/
sudo nvram fa4ce28d-b62f-4c99-9cc3-6815686e30f9:gpu-power-prefs=%01%00%00%00
reboot
```

Back into Single User Mode - CMD-S held down on boot.  When you see the terminal, hit enter a couple of times to get the prompt.  Then run:
```
csrutil enable
reboot
```
