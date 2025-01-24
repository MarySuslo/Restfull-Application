FROM openjdk:23

ADD  ./src/main/resources/db_migration.sql db.sql

COPY  ./out/artifacts/restfull_jar/restfull.jar restfull.jar
COPY  ./src/main/resources/db_migration.sql db.sql

CMD ["java","-jar","restfull.jar","sql","db.sql"]
EXPOSE 8080:5432