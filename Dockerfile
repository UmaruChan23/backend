FROM openjdk:11.0.9-jre-slim

RUN groupadd -g 1001 activec && useradd -u 1001 -g activec -m -s /bin/bash activec

COPY --chown=activec:activec ./build/libs/backend-0.0.1-SNAPSHOT-all.jar /opt/backend.jar

EXPOSE 5683
EXPOSE 8080

USER activec

ENTRYPOINT java -Xmx2G -jar /opt/backend.jar