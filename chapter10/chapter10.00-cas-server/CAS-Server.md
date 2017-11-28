# README #

## CAS-Server

The server home page is located at: https://apereo.github.io/cas/5.1.x/index.html

This module was based on the Gradle overlay:
https://github.com/apereo/cas-gradle-overlay-template

Only a few modifications have been made on this overlay

## Overlay Changes ##
* Updated the ./etc/cas/config/cas.properties
* Added 'tomcat.keystore' from keys generated in Chapter 08.
* Made a copy of 'tomcat.keystore' to name of 'thekeystore' (the CAS default keystore name)

** additional information about creating the keystore can be found:
https://tomcat.apache.org/tomcat-8.0-doc/ssl-howto.html


## CAS Server Information ##

### Server project: ###
cas-gradle-overlay-template-master


### Default URL (SSL Enabled): ###
https://127.0.0.1:9443/cas/


### Default URL (SSL disabled): ###
http://127.0.0.1:9443/cas/


#### Default login Credentials: ####
username: casuser
password: Mellon

