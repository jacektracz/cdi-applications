# README

This directory contains configuration files and information for setting up a
local development environment, compliant with the coding style and rules in use
at XIAM Solutions B.V.

## Recommended local development environment
This is the development software stack we are using ourselves. This stack is battle tested and
guaranteed to work.
+ Ubuntu Desktop 14.04.04 LTS (x64)
+ Oracle JDK, version 6u45 for building the open source artifacts
+ Oracle JDK, version 8u74 (for all other purposes)
+ Maven 3 (3.2.5) and Git 1.9.1 available from shell
+ Netbeans IDE 8.1

## Target runtime environment (PRODUCTION)
Starting early 2010 all our server (production) environments are based on this stack. 
Currently (February 2016) this stack consists of:
+ Debian 8.3 (all server environments), x64
+ Managed by Ansible, version 2.0.x
+ Oracle Server JRE, version 8 (latest)
+ WildFly 10

Both the local developments and the server environments are setup and managed by 
[ansible](http://ansible.cc). A highly recommended Software Configuration Management (SCM) 
tool.

## Checkstyle configuration
Checkstyle configuration files can be found in this directory and are referenced from the
parent Maven POM. If you are using Eclipse, you have to configure Eclipse using these files.
Note that Checkstyle warnings should be minimized. Checkstyle errors are always forbidden
and will make the build fail.

## Netbeans configuration
+ Both Oracle JDK version 6 and 8 should be installed
+ Install Netbeans (installation directory is references by $NETBEANS_HOME)
+ Run Netbeans using the Oracle JDK version 8. Add the following line in 
$NETBEANS_HOME/etc/netbeans.conf
```
netbeans_jdkhome="/usr/lib/jvm/java-8-oracle"
```
+ Import the standard configuration from this directory (netbeans-options.zip)
```
Tools > Options > Import > Select netbeans-options.zip > Select all options to import > OK
```
