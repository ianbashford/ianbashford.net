---
title:  "Intellij in a docker container - Wayland Sway"
date: 2019-10-27T14:18:47Z
draft: false
---
### Intro
This combines a couple of tricks; running a java gui inside a docker container, in wayland sway.

### Dockerfile 
Most of this came from github, with a few tweaks... 
```
# Alpine 3.8 C++/Java Developer Image

FROM alpine:latest

ENV LANG C.UTF-8

RUN set -ex && \
	apk --no-cache --update add \
	# basic packages
		bash bash-completion coreutils file grep openssl openssh nano sudo tar xz \
	# debug tools
		gdb musl-dbg strace \
	# docs and man
		bash-doc man man-pages less less-doc \
	# GUI fonts
		font-noto \
	# user utils
		shadow

RUN set -ex && \
	apk --no-cache --update add \
	# C++ build tools
		cmake g++ git linux-headers libpthread-stubs make

RUN set -ex && \
	apk --no-cache --update add \
	# Java tools
		gradle openjdk8 openjdk8-dbg

# Install IntelliJ Community
RUN set -ex && \
	#wget https://download-cf.jetbrains.com/idea/ideaIC-2019.1.1-no-jbr.tar.gz && \
	wget https://download.jetbrains.com/idea/ideaIC-2019.2.3-jbr8.tar.gz && \
	tar -xf ideaIC-2019.2.3-jbr8.tar.gz  && \
	rm ideaIC-2019.2.3-jbr8.tar.gz 

# Create a new user with no password
ENV USERNAME developer
RUN set -ex && \
	useradd --create-home --key MAIL_DIR=/dev/null --shell /bin/bash $USERNAME && \
	passwd -d $USERNAME

USER developer

# Set additional environment variables
ENV JAVA_HOME /usr/lib/jvm/java-1.8-openjdk
ENV JDK_HOME  /usr/lib/jvm/java-1.8-openjdk
ENV JAVA_EXE  /usr/lib/jvm/java-1.8-openjdk/bin/java

ENTRYPOINT ["/idea-IC-192.6817.14/bin/idea.sh"]
```

##### Build the docker image
```
docker rmi intellij
docker build -t intellij .
```
Like a lot of images like this, its rather heavy (1.9G)

##### Running in Wayland Sway
```
docker run --rm -it \
	-v /tmp/.X11-unix:/tmp/.X11-unix \
	-e XDG_RUNTIME_DIR=/tmp \
	-e WAYLAND_DISPLAY=wayland-0 \
	-v /run/user/1000/wayland-0:/tmp/wayland-0 \
	-e DISPLAY=:0 \
	--env XDG_SESSION_TYPE=wayland \
	--env _JAVA_AWT_WM_NONREPARENTING=1 \
	--name intellij intellij
```
