+++
title = "Docker in IPV4 and IPV6 on Digital Ocean with a floating IP address"
date = "2018-08-21T21:53:52Z"
draft = "false"

+++
### Intro
With a floating IPV4 address, the traffic comes into the droplet via the anchor ip address.  This is a private IP address in the 10.0.0.0 network.
Docker options setup IPV4 and IPV6 neatly, then the default route needs adjusting, otherwise traffic is routed out via the droplets IP address

### Docker configuration
Depending on your linux variant, you'll need to set some DOCKER_OPTS options.  
1. Determine the anchor IP address
2. Determine the IPV6 subnet
3. Add DOCKER_OPTS

##### Determine the anchor IP address
```
curl -s http://169.254.169.254/metadata/v1/interfaces/public/0/anchor_ipv4/gateway
```

##### Determine the IPV6 subnet
```
ip -6 addr
```
Find the line for eth0 starting with inet6.  Don't use the line fe80:: - that's the link local.  Then I use this [subnet calculator](http://www.gestioip.net/cgi-bin/subnet_calculator.cgi) to get the network.
Put that together for your linux distribution - you'll need the following three options
```
--ip=<anchor address>
--ipv6
--fixed-cidr-v6=<ipv6 subnet>/64
```
This is a systemd (works with CoreOs)  `/etc/systemd/system/docker.service.d/10-ipv6.conf`
```
[Service]
Environment=DOCKER_OPTS="--ip=10.1.0.72 --ipv6 --fixed-cidr-v6=20a3:b1d9:3:d1::/64"
```

### Routing
Final part - outbound traffic needs to look like it comes from the floating ip.  That means sending back to the anchor ip address.  Something like this should run at boot:
```
#!/bin/bash
route del default && route add default gw 10.1.0.72 eth0
```

