FROM openjdk:21-jdk-slim

WORKDIR /app

# Copia o jar da aplicação
COPY target/*.jar app.jar

# Expõe a porta
EXPOSE 8080

# Configura healthcheck
HEALTHCHECK --interval=30s --timeout=10s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Executa a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
