FROM openjdk:8
WORKDIR /school-registration-system
COPY . .
RUN mvn clean install
CMD mvn spring-boot:run
