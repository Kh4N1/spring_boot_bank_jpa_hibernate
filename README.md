### start:
```bash
docker network create postgres_network
```
```bash
docker run -d \
    --name postgres_db \
    --network postgres_network \
    -e POSTGRES_USER=khani \
    -e POSTGRES_PASSWORD=boom \
    -e POSTGRES_DB=mokhtar \
    -p 5432:5432 \
    -v postgres_data:/var/lib/postgresql/data \
    postgres:15
```
```bash
docker run -d \
    --name pgadmin4 \
    --network postgres_network \
    -e PGADMIN_DEFAULT_EMAIL=admin@admin.com \
    -e PGADMIN_DEFAULT_PASSWORD=admin \
    -p 5050:80 \
    dpage/pgadmin4
```