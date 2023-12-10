FROM openjdk:17-jdk-slim
ADD *.jar /app/app.jar
ADD entrypoint.sh /app/
ADD preStop.sh /app/
RUN apt-get update \
    && apt-get install -y curl \
    && chmod +x /app/entrypoint.sh /app/preStop.sh \
    && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo "Asia/Shanghai" > /etc/timezone
ENTRYPOINT  ["/app/entrypoint.sh"]
EXPOSE 8001
STOPSIGNAL SIGTERM