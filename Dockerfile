#FOR MAVEN!
#STEP 1,AMBIL IMAGE MAVEN yang menggunakan jdk 17
FROM maven:3.8.8 AS build
#ATUR LABEL

#STEP 2, DEFINE ENV VARIABLE untuk mengganti properties di application.properties (SPRING BOOT)
ARG app_port=8080
ARG db_host=localhost
ARG db_port=5433
ARG db_username=postgres
ARG db_password=Afandayu11
ARG db_name=wallet_db

#STEP 3,  SET WORKING DIRECTORY
WORKDIR /app

#STEP 4, COPY Pom.xml to workdir
COPY pom.xml .

#STEP 5, INSTALL DEPENDENCIES (needed libraries)
#RUN mvn dependency:go-offline -B

#STEP 5, COPY SOURCE CODE TO WORKDIR
COPY src ./src

#STEP 6, BUILD APP with Maven (Adding args to change the properties in application.properties)
RUN mvn clean package -DskipTests -Dapp.port=${app_port} -Ddb.host=${db_host} -Ddb.port=${db_port} -Ddb.username=${db_username} -Ddb.password=${db_password} -Ddb.name=${db_name}




#FOR JDK
#STEP 1, GUNAKAN IMAGE OPENJDK 17 JRE slim untuk runtime
FROM openjdk:17-jdk-alpine

#STEP 3, ATUR DIRECTORY KERJA UNTUK APLIKASI
WORKDIR /app

#STEP 4, COPY FILE JAR FROM IMAGE BUILD TO IMAGE RUNTIME
COPY --from=build /app/target/wallet-0.0.1-SNAPSHOT.jar ./wallet-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "./wallet-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080