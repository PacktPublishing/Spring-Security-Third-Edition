##---------------------------------------------------------------------------##
##
##---------------------------------------------------------------------------##
# We're using the official OpenJDK image from the Docker Hub (https://hub.docker.com/_/openjdk/).
# Take a look at the available versions so you can specify the Java version you want to use.
FROM java:openjdk-8-jdk

# Set the WORKDIR. All following commands will be run in this directory.
WORKDIR /app

# Clone the conf files into the docker container
#RUN git clone git@bitbucket.org:mickknutson/jbcpcalendar.git
RUN git clone https://mickknutson@bitbucket.org/mickknutson/jbcpcalendar.git /app


RUN echo $PWD

#RUN ls -la
#RUN find . -maxdepth 4 -exec du -ms {} \; | sort -rn | head -n 45


# Install the gradle version used in the repository through gradlew
RUN ./gradlew check -Pfast

# Send Build Scan Results:
RUN ./gradlew bSPP

# Run gradle assemble to install dependencies before adding the whole repository
#RUN gradle test

#ADD . ./

#RUN ls -la
#RUN find . -maxdepth 4 -exec du -ms {} \; | sort -rn | head -n 45


##---------------------------------------------------------------------------##
##---------------------------------------------------------------------------##
