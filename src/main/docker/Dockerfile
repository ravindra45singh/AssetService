# original source: https://github.com/docker-library/openjdk/blob/b118fdc1e9b1aebdc178537551101dffe1f612a3/8-jre/alpine/Dockerfile
FROM de.telekom.iris/iris-baseimage

# yes, in alpine linux it is sh and not bash
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

expose 9004
expose 9090