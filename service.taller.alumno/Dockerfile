#FROM amazoncorretto:21-alpine
FROM --platform=linux/amd64 amazoncorretto:21-alpine
# resto del Dockerfile...
LABEL authors="williamssandroponteramirez"
LABEL version="1.0"
LABEL descripcion = "Servicio que se subirá a la nube de AWS EC2"
VOLUME /tmp
EXPOSE 80
COPY build/libs/service.taller.alumno*.jar service.taller.alumno.jar
ENTRYPOINT ["java", "-jar", "service.taller.alumno.jar"]