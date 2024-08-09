## Kardo-backend

### System requirements:
JDK 17 amazon corretto  

PostgreSQL

IntellijIdea

Spring Boot 3

### Startup instructions:
1. Git clone or Download zip-file 
2. Unpack zip-file
3. Open app in IntellijIdea
4. Use you PostgresDB properties in config:
   - DB_HOSTNAME;
   - DB_PORT;
   - DB_NAME;
   - POSTGRES_NAME; 
   - POSTGRES_PASSWORD;
5. mvn package spring-boot:repackage
6. run app from main class or from terminal (jar file in /target)

http://localhost:8080/swagger-ui/index.html

https://kardo.zapto.org/swagger-ui/index.html

http://51.250.32.102:8080/swagger-ui/index.html