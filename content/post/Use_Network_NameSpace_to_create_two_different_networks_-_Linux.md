---
title: "Use Network NameSpace to create two different networks   Linux"
date: 2019-10-27T18:24:59Z
draft: false
---
### Intro
Here's the setup: you have two different gateways, one accessing the internet via a VPN, and one straight out of your home router.  You'd like some processes to only use the VPN gateway, and some to just access the internet via your standard router.
Solution: create a new network namespace with a default gateway that uses the VPN.  Start any processes that should use the VPN in that network namespace.  Here's how...

### Script
Here's a script to setup the new network namespace.  In this instance, 192.168.3.2 is my VPN gateway, and 192.168.3.192 is an additional IP address that'll be assigned to this virtual adapter. Needs to be run as root:
```
#!/usr/bin/bash
# derived from: https://sgros.blogspot.com/2017/04/how-to-run-firefox-in-separate-network.html
ip netns add vpnns
ip link add link eno1 name vpnnet type macvlan
ip link set vpnnet netns vpnns
ip netns exec vpnns ip link set vpnnet up
ip netns exec vpnns ip addr add 192.168.3.192/24 dev vpnnet
ip netns exec vpnns ip route add default via 192.168.3.2
```

##### Setup a userful alias
```
alias safemode='sudo ip netns exec safens sudo -u <YOUR USERNAME> '
# e.g. alias safemode='sudo ip netns exec safens sudo -u developer '
```

##### Trying it out with curl
```
» curl https://api.ipify.org
#then
safemode curl https://api.ipify.org
```

##### Trying it out with browsers
Chrome's a little tricky; once there's one chrome instance running, all windows will join that initial process, so you'll need two browsers.  I used brave as a second browser.  Access this URL in each: 
```
https://api.ipify.org/?format=json
```

```
google-chrome-stable --incognito
```
and
```
safemode brave-nightly
```

