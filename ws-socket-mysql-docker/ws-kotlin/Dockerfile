FROM openjdk:8-slim
EXPOSE 8890

ENV JAVA_OPTS=""

ADD ./target/pro-socket-1.0.0.jar app.jar

RUN sh -c 'touch /app.jar'

CMD [ "java", "-jar" ,"app.jar" ]
