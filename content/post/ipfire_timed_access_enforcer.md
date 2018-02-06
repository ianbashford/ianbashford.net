+++
date = "2018-02-06T20:46:33Z"
title = "IPFire - Timed Access Enforcer"
draft = "false"

+++
## IPFire - enforcing timed access
### Timed Access Issues
The IPTables rules in IP Fire have one limitation for enforcing timed access.
>ATTENTION: Rules which have Time constraints configured are only for new connections. Example: if you are blocking Internet connections from 20:00 to 6:00, and you already have a connection established at 19:57, this connection will be allowed until it is closed. Any new connection after 20:00 will be dropped.>

### Reasons
In my case, its this ordering (shown in a snip from iptables -L --line-numbers)
```
Chain FORWARD (policy DROP)
num  target     prot opt source               destination
1    BADTCP     tcp  --  anywhere             anywhere
2    CUSTOMFORWARD  all  --  anywhere             anywhere
3    TCPMSS     tcp  --  anywhere             anywhere             tcp flags:SYN,RST/SYN TCPMSS clamp to PMTU
4    P2PBLOCK   all  --  anywhere             anywhere
5    GUARDIAN   all  --  anywhere             anywhere
6    IPSECBLOCK  all  --  anywhere             anywhere             policy match dir out pol none
7    OVPNBLOCK  all  --  anywhere             anywhere
8    OVPNBLOCK  all  --  anywhere             anywhere
9    IPTVFORWARD  all  --  anywhere             anywhere
10   LOOPBACK   all  --  anywhere             anywhere
11   CAPTIVE_PORTAL  all  --  anywhere             anywhere
12   CONNTRACK  all  --  anywhere             anywhere
13   GEOIPBLOCK  all  --  anywhere             anywhere
14   IPSECFORWARD  all  --  anywhere             anywhere
15   WIRELESSFORWARD  all  --  anywhere             anywhere             ctstate NEW
16   FORWARDFW  all  --  anywhere             anywhere
17   UPNPFW     all  --  anywhere             anywhere             ctstate NEW
18   REDFORWARD  all  --  anywhere             anywhere
19   POLICYFWD  all  --  anywhere             anywhere>
```
Note that CONNTRACK rules (12) are assessed before my own IPFire firewall rules (16)
The basic CONNTRACK rule in there is to ACCEPT existing connections
```
Chain CONNTRACK (3 references)
num  target     prot opt source               destination
1    ACCEPT     all  --  anywhere             anywhere             ctstate ESTABLISHED
```

## Solution
Cdoe on github here: https://github.com/ianbashford/ipfire_timed_access_enforcer

### TL;DR
use  conntrack -D -s <ip address> to clear connections straight after the timed access kicks in
Instructions are on the github page...


