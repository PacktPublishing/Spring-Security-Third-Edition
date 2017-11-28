# README #

## Client Certificate Authentication ##

Complete


### Sections ###

#### 08.00-calendar ####
BASE Line implementation based on chapter07.08


#### 08.01-calendar ####
Implementation using standard X509Configurer:
http.x509().userDetailsService(userDetailsService);



#### 08.02-calendar ####
Implementation including .subjectPrincipalRegex("CN=(.*?),") 
and Http403ForbiddenEntryPoint



#### 08.03-calendar ####
Implementation including X509AuthenticationFilter

