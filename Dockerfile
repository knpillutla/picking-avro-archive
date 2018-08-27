FROM java:8-jre
VOLUME /tmp
ADD target/picking-0.0.1-SNAPSHOT.jar /app.jar
RUN mkdir -p /avro
COPY target/resources/avro/* /avro/
# All Spring Boot components expose 8000 as the container java debug port
EXPOSE 8000
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=azure","-jar","/app.jar"]