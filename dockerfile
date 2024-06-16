# Use uma imagem base do Maven para construir a aplicação
FROM amd64/maven:3.9-amazoncorretto-8-debian AS build

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

RUN apt-get update
RUN apt-get install -y git

# Clona o repositório do GitHub
RUN git clone https://github.com/raphaelfilipemattos/minerva.git .

# Mude para o diretório 'back' onde está o projeto Spring Boot
WORKDIR /app/back

# Executa o build do projeto
RUN mvn -X clean package -DskipTests

# Use uma imagem base do OpenJDK para rodar a aplicação
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o arquivo JAR construído da imagem anterior
COPY --from=build /app/back/target/*.jar app.jar

# Expõe a porta que a aplicação irá rodar
EXPOSE 8080

# Define variáveis de ambiente para a conexão JDBC
ENV JDBC_DATABASE_URL=""
ENV JDBC_DATABASE_USERNAME=""
ENV JDBC_DATABASE_PASSWORD=""

# Comando para rodar a aplicação Spring Boot
ENTRYPOINT ["java","-jar","/app/app.jar"]
