FROM openjdk:11.0.12-jre-slim-buster
# Indicando el puerto para exponer, debo pasar el flag -p para habilitarlo o -P para publicarlos todos.
EXPOSE 8080
# creando la carpeta para el proyecto
RUN mkdir /app

COPY build/libs/*.jar app.jar
#Comando que se ejecuta una vez es iniciada la aplicación.
ENTRYPOINT ["java", "-jar", "app.jar"]
