FROM azul/zulu-openjdk:11-latest

COPY ./build/libs/gg-pigs-api-*.jar /app/app.jar

WORKDIR /app

CMD ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]
