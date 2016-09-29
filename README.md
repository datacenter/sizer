# sizer
Cisco Network Sizer is a Web based application which helps in sizing ACI or Nexus 9000 based Network infrastructure for a data centre.

Network Sizer consists of three high level modules,

- Logical Sizer: For a given workload/application, it calculates the optimum number of leaf switches required. It also gives information about the utilization of these switches with respect to various switch attributes.
- Physical Sizer: Calculates number of leafs, spine switches, and port termination statistics for a datacentre based on server, switches and rack characteristics.
- Repository: The repository objects can be used in importing application specific constraints/configuration from third party devices. These configuration can be used in logical sizer contracts.


## Pre-requisites:
These are pre-requisites for setting up a build environment and running application locally.
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

