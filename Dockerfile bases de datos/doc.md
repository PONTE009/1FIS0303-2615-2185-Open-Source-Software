# Dockerizando
```dockerfile
# Ejecutar un docker compose tradicional
docker compose up -d

# Dockerizar bases de dato
docker compose -f database.yaml --env-file localhost.env up -d

# Correr un servicio en específio
docker compose -f database.yaml --env-file localhost.env up -d postgres
```
#### Para conectarte por CloudBeaver Comunity a una base de datos dockerizada en el host se tiene que colocar *host.docker.internal*