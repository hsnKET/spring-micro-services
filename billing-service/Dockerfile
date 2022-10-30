# define base docker image
FROM openjdk:8
EXPOSE 8761
ADD target/billing-service.jar billing-service.jar
ENTRYPOINT ["java","-jar","billing-service.jar"]
