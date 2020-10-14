Prerequisits(to be installed):
- java
- maven
- docker
- docker-compose
- git

Projects use 1.8 Java version.

Steps for running the apigateway app:

1. clone apigateway from: https://github.com/vbradic/apigateway.git
2. clone userservice from: https://github.com/vbradic/userservice.git
3. to run a mongodb navigate to userservice/mongodb/ and run docker-compose up -d
4. from userservice root path, run: mvn clean install
5. after step 4 finishes, from the apigateway root path run: mvn clean install
6. after step 5 finishes, from the root of apigateway run: java -jar ./target/apigateway-0.0-1-SNAPSHOT.jar
