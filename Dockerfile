FROM openjdk:17

WORKDIR /usr/src/app

ARG JAR_PATH=./build/libs

COPY ${JAR_PATH}/sgdc-0.0.1-SNAPSHOT.jar ${JAR_PATH}/sgdc-0.0.1-SNAPSHOT.jar

CMD ["java","-jar","./build/libs/sgdc-0.0.1-SNAPSHOT.jar"]