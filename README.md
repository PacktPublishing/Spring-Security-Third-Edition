# Spring Security 3rd Edition README

This README would normally document whatever steps are necessary 
to get your application up and running.

## JBCP Calendar Application

* Jim Bob CP Calendar Application
* 4.00.00-SNAPSHOT
* [Pact Publishing Book Home](https://www.packtpub.com/application-development/spring-security-third-edition)
* [BitBucket](https://bitbucket.org/mickknutson/jbcpcalendar/)
* [codecov](https://codecov.io/gh/mickknutson)

This code has been developed in [Gradle](http://gradle.org) and has 
a sub-project for each chapter milestone.
[Thymeleaf](http://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html) has been used as
the view templating engine throughout the book


### Chapters
***

1. [Anatomy of an Unsafe Application](chapter01/README.md)
2. [Getting Started with Spring Security](chapter02/README.md)
3. [Custom Authentication](chapter03/README.md)
4. [JDBC-Based Authentication](chapter04/README.md)
5. [Authentication with Spring-Data](chapter05/README.md)
6. LDAP Directory Services
7. Remember-Me Services
8. Client Certificate Authentication with TLS
9. Opening up to OAuth2
10. Single Sign-on with Central Authentication Service
11. Fine-grained Access Control
12. Access Control Lists
13. Custom Authorization
14. Session Management
15. Additional Spring Security Features
16. Microservice security with OAuth2 and JSON Web Tokens
17. Migration to Spring Security 4.2

* * * 

***

*****

- - - -

-----------------------


# Building and Running the code examples

Code Software Requirements
=
* JDK 8
* Gradle 4.x
* IntelliJ 2017+
* Eclipse Neon+
* See 'gradle.properties' for list of dependency versions


Building Intellij Project Files:
=
./gradlew idea

[Intellij Plugin](https://docs.gradle.org/current/userguide/idea_plugin.html)


Building Eclipse Project Files:
=
./gradlew eclipse

[Eclipse Plugin](https://docs.gradle.org/current/userguide/eclipse_plugin.html)


Clearing Gradle Wrapper cache:
-
(from project root) ./gradlew --build-cache clean


Clearing Gradle install cache:
-
rm -rf $HOME/.gradle/caches/
rm -rf $HOME/.gradle/daemon/


Running Individual projects:
-
* Chapter 01: (from chapter root) ./gradlew tomcatRun
* Chapter 02: (from chapter root) ./gradlew tomcatRun
* Chapter 03: (from chapter root) ./gradlew tomcatRun
* Chapter 04: (from chapter root) ./gradlew tomcatRun
* Chapter 05: (from chapter root) ./gradlew bootRun


Running SonarCube for any individual project:
-

(from PROJECT root) ./gradlew sonarqube \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=cbb0017712d39d6e9799a626cc0d980b0dd620e1


./gradlew sonarqube -Dscan


-- Note: Sonar server must be running !!!



Misc Items
-
gradle chapter16:dependencyInsight --dependency commons-lang3 --configuration compile



### TODO's
* From Chapter15, add favicon's images to all projects.
* Get project to build in Circle-CI
* Get WebDriver tests to work.
* Appy http://stehno.com/gradle-site/
* Appy http://stehno.com/gradle-webpreview-plugin/
* Need to enable SonarQube for all modules
* Move ./config scripts to be init-scripts [init-scripts](https://docs.gradle.org/current/userguide/init_scripts.html)
* Integrate CircleCI to [FOSSA](https://fossa.io/docs/integrating-tools/circleci/)



## the end... ##
***