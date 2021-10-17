---
title: "SetupCloudFlareWarpLinuxArch_Update"
date: 2021-10-17T15:45:53Z
lastmod: 2021-10-17T15:45:53Z
draft: false
keywords: []
description: ""
tags: []
categories: []
author: ""

# You can also close(false) or open(true) something for this content.
# P.S. comment can only be closed
comment: false
toc: false
autoCollapseToc: false
postMetaInFooter: false
hiddenFromHomePage: false
# You can also define another contentCopyright. e.g. contentCopyright: "This is anothe
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
This is a more recent update from [setupcloudflarewarplinuxarch](/post/setupcloudflarewarplinuxarch/)
Warp's a free VPN offered by Cloudflare -- see [Cloudflare](https://1.1.1.1/)
Clients are currently available for Android, IOS, OSX, Windows and now Linux [Warp](https://1.1.1.1/)

Here's how to set it up on linux from the command line

# Scripted Solution
```
#! /bin/bash
yay -S cloudflare-warp-bin
sudo systemctl start warp-svc
sleep 5
warp-cli register
warp-cli status
warp-cli connect
sleep 5
curl https://www.cloudflare.com/cdn-cgi/trace/ 2>/dev/null  |grep warp
```

The sleep statements are just there to give the system chance to get started

You can use Cloudflare's own setup instructions which are here [linux desktop client](https://developers.cloudflare.com/warp-client/setting-up/linux)

To disconnect you can use
```
warp-cli disconnect
```
