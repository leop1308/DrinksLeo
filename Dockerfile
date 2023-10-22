FROM openjdk

WORKDIR /app

COPY target/drinksleo-0.1-SNAPSHOT.jar /app/drinksleo.jar

ENTRYPOINT ["java", "-jar", "drinksleo.jar"]