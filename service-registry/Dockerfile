FROM java:8-jre

ENV MICROSERVICE_FILE target/service-registry-0.0.1-SNAPSHOT.jar

# Set the location of the verticles
ENV MICROSERVICE_HOME /opt/verticles

EXPOSE 8761

COPY $MICROSERVICE_FILE $MICROSERVICE_HOME/


WORKDIR $MICROSERVICE_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["java  -jar service-registry-0.0.1-SNAPSHOT.jar"]
