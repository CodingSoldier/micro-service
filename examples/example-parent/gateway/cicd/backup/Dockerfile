FROM openjdk:17-jdk-slim
ADD *.jar /app/app.jar
ADD entrypoint.sh /app/
RUN chmod +x /app/entrypoint.sh \
    && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo "Asia/Shanghai" > /etc/timezone
ENTRYPOINT  ["/app/entrypoint.sh"]
EXPOSE 8000
STOPSIGNAL SIGTERM