# sizer
This directory contains all the source files related to sizer project.

## Pre-requisites:
- Install postgresql and create database.
- Update username, password, and database name in application-dev.yml
- Install Node components

## Build
- Run mvn install -DskipTests=true
    This installs bower and other components and builds war file.
- To run application locally, use the command,
    mvn spring-boot:run


## Build Plugins: Palo Alto Plugin:
- Go to directory paloPlugin
- Run mvn install -DskipTests=true. This builds jar file.
- The location of the jar file is configured in application database, plugins table. Copy built jar file to this location.


## Deployment using OVA:

An OVA consisting of Ubuntu 16.04 LTS with application is available for download from 
https://cisco.box.com/s/rbtrpogbhvvi8hqadpeami5udz5o32m1


To setup a server, please follow below steps.

- Deploy OVA. Refer userguide-ova-deployment under docs directory for details.
- Boot the VM, notedown the IP address of VM
- Access the application from the link https://ip address/

