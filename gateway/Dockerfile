# define base docker image
FROM openjdk:8
EXPOSE 9999
ADD target/gateway.jar gateway.jar
ENTRYPOINT ["java","-jar","gateway.jar"]
