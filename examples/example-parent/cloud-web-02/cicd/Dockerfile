FROM openjdk:17-jdk-slim
ADD *.jar /app/app.jar
ADD entrypoint.sh /app/
ADD preStop.sh /app/
RUN chmod +x /app/entrypoint.sh /app/preStop.sh \
    && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo "Asia/Shanghai" > /etc/timezone
ENTRYPOINT  ["/app/entrypoint.sh"]
EXPOSE 8002
STOPSIGNAL SIGTERM