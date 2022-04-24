FROM eclipse-temurin:17-jre-focal

ARG APP_HOME=/app
WORKDIR $APP_HOME
COPY target/hello-ok-otus.jar $APP_HOME/hello-ok-otus.jar

EXPOSE 8000

ENTRYPOINT exec java $JAVA_OPTS  -jar ./hello-ok-otus.jar