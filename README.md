4. Como Usar
Para desenvolvimento local:
bash
# Use o perfil padrão (application.properties)
mvn spring-boot:run
Para Docker:
bash
# 1. Build dos JARs
mvn clean package -DskipTests

# 2. Subir tudo com Docker Compose
docker-compose up -d

# 3. Verificar logs
docker-compose logs -f user-service
