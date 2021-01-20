FROM openjdk:11.0.9.1-oracle
VOLUME /tmp
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
COPY build/libs/emailflow-service-0.0.1-SNAPSHOT.jar emailflowservice.jar
EXPOSE 8045
ENTRYPOINT exec java $JAVA_OPTS -jar emailflowservice.jar
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
#ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar emailflowservice.jar
