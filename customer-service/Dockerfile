# define base docker image
FROM openjdk:8
EXPOSE 8761
ADD target/customer-service.jar customer-service.jar
ENTRYPOINT ["java","-jar","customer-service.jar"]
