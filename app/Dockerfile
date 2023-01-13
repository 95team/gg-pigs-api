FROM azul/zulu-openjdk:11-latest

COPY ./build/libs/gg-pigs-api-*.jar /app/

WORKDIR /app

CMD ["java", "-jar", "gg-pigs-api.jar", "--spring.profiles.active=prod"]
