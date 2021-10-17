---
title: "Setup CloudFlare Warp on Linux (Arch)"
date: 2020-08-15T14:02:43Z
lastmod: 2021-10-17T10:02:43Z
draft: false
keywords: [cloudflare, 1.1.1.1, warp, archlinux]
description: ""
tags: []
categories: []
author: "eanu"

# You can also close(false) or open(true) something for this content.
# P.S. comment can only be closed
comment: false
toc: false
autoCollapseToc: false
postMetaInFooter: false
hiddenFromHomePage: false
# You can also define another contentCopyright. e.g. contentCopyright: "This is another copyright."
contentCopyright: false
reward: false
mathjax: false
mathjaxEnableSingleDollar: false
mathjaxEnableAutoNumber: false

# You unlisted posts you might want not want the header or footer to show
hideHeaderAndFooter: false

# You can enable or disable out-of-date content warning for individual post.
# Comment this out to use the global config.
#enableOutdatedInfoWarning: false

flowchartDiagrams:
  enable: false
  options: ""

sequenceDiagrams: 
  enable: false
  options: ""

---

# Update Available
There's an updated version of this post [SetupCloudFlareWarpLinuxArch_Update](/post/setupcloudflarewarplinuxarch_update/)

# Original Content

Warp's a free VPN offered by Cloudflare -- see [Cloudflare](https://1.1.1.1/) 
Clients are currently available for Android and IOS, with OSX and Windows in beta [Warp Beta](https://1.1.1.1/beta/)

Here's how to set it up on linux from the command line

### Setup Wireguard and the Cloudflare configuration tool
If you don't use arch linux, go to [wireguard installation](https://www.wireguard.com/install/)
```
sudo pacman -S wireguard-tools
yay -S wgcf
```

You'll need to keep the wireguard-tools installed, but wgcf is a onetime use

### Setup Cloudflare profiles

```
wgcf register
wgcf generate
```

### Copy the config 
```
sudo cp wgcf-profile.conf /etc/wireguard
```

### Start / Stop / Enable
Start
```
wg-quick up wgcf-profile
```
Stop
```
wg-quick up wgcf-profile
```
Enable
```
systemctl enable wg-quick@wgcf-profile
```

### Tweaks
I like to use my local dns so I can resolve hosts on the local network - this setup uses 1.1.1.1 by default.
Since I use the excellen [dnscrypt](https://github.com/dnscrypt/dnscrypt-proxy), I'm comfortable doing this...
```
sudo vi /etc/wireguard/wgcf-profile.conf
```
and change the line  ```DNS = 1.1.1.1```   to your local secure dnscrypt server.

