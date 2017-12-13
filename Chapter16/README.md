# README #

### Microservice Security with OAuth2 and JSON Web Tokens ###

Not Done


### Sections ###

#### 16.00-calendar ####
Base line Starting from chapter16.00
But minus all ThymeLeaf



## OAuth2 Resource Server Example

Running Example
-
(from ./chapter16.00/) gradle bootRun

Spring Profiles
-
To enable profiles, just append:





### Request Token:
$ http -a oauthClient1:oauthClient1Password -f POST localhost:8080/oauth/token grant_type=password username=user1@example.com password=user1

$ curl oauthClient1:oauthClient1Password@localhost:8080/oauth/token -d grant_type=password -d username=user1@example.com -d password=user1 -v

or:

$ curl oauthClient1:oauthClient1Password@localhost:8080/oauth/token -d "grant_type=password&username=user1@example.com&password=user1" -v

### Use Token:

$ http localhost:8080/ "Authorization: Bearer [access_token]"

$ curl -H "Authorization: Bearer XXX" localhost:8080/ -v

or

$ curl -H "Authorization: Bearer [token]" oauthClient1:oauthClient1Password@localhost:8080/ -v


### TLS Usage

curl oauthClient1:oauthClient1Password@localhost:8443/oauth/token -d "grant_type=password&username=user1@example.com&password=user1" -v
* Note, I think there is an actual issue with the handshake for 'curl'
I suspect that I need more than one request to negotiate the Certificate,
before making the 'oauth/token' request over TLS


## Docker

https://mydevgeek.com/spring-boot-docker/


./gradlew buildDocker
