#!/bin/bash
## rerun all docker commands to create a new database instance
docker stop myprj2database
docker rm myprj2database
docker rmi mydbimage:0.1 
docker build -t mydbimage:0.1 .
docker run -d -p 5433:5432 --name myprj2database mydbimage:0.1
docker logs myprj2database
docker ps

## show it works

sleep 3

echo 'table president;' | psql  -p 5433 -h localhost -U exam presidents
echo alias pres='psql  -p 5433 -h localhost -U exam presidents'
