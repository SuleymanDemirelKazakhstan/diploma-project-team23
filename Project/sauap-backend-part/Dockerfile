FROM openjdk:11-jdk-slim as builder

WORKDIR /app
ARG JAR_FILE=target/sauap-backend-*.jar
COPY ${JAR_FILE} application.jar
RUN jar xvf application.jar

FROM openjdk:11-jre-slim

ARG DEPENDENCY=/app
COPY --from=builder ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=builder ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=builder ${DEPENDENCY}/BOOT-INF/classes /app

ENV TZ=Asia/Almaty

HEALTHCHECK --start-period=90s --interval=60s --timeout=15s CMD /usr/bin/curl -sS --fail http://localhost:8080/actuator/health || exit 1
ENTRYPOINT ["sh", "-c","java $JAVA_OPTS -cp app:app/lib/* kz.sdu.project.sauapbackend.SauapBackendApplication"]