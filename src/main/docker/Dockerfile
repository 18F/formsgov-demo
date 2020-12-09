FROM adoptopenjdk:11-jre-hotspot

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    faasuser_SLEEP=0 \
    JAVA_OPTS=""

# Add a faasuser user to run our application so that it doesn't need to run as root
RUN adduser -D -s /bin/sh faasuser
WORKDIR /home/faasuser

ADD entrypoint.sh entrypoint.sh
RUN chmod 755 entrypoint.sh && chown faasuser:faasuser entrypoint.sh
USER faasuser

ENTRYPOINT ["./entrypoint.sh"]

EXPOSE 8181 5701/udp

ADD *.jar app.jar

