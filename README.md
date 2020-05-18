![Maven build status](https://github.com/FontysVenlo/jersey2-example/workflows/Java%20CI%20with%20Maven/badge.svg?branch=master)

# Simple Jersey 2 example
Simple jersey 2 start application with **president example database** - used for Projects at Fontys Venlo.

This example is just a *start*, this means, no detailed explanations will be given as well as no *ready* implementation. It is also **not** mandatory to do it like this or to take everything like provided, you need to adapt it for your project.

The structure is as follows:

- You use a docker container for the database. The same thing as last semester, but now we will create an own docker container for that based on the postgres container.
    - why? Because we want to have the database be filled already with our testdata, in this case the president database. Nice, right?
- Then you run the application locally via jetty on your machine and this connect with the postgres database on your docker container

## Howto run

- clone first `git clone https://github.com/sebivenlo/jersey2-example.git`
    - (if you want to use it as base for your project, use this as a template, see button above near cloning button)
- then of course change to the directory `cd jersey2-example`

![clone and change directory](https://user-images.githubusercontent.com/764295/79967324-f9d61200-848e-11ea-8f57-d875795f4651.gif)

- build your own docker container based on the [Dockerfile](Dockerfile). Have a look into it how it works.

```bash
docker build -t mydbimage:0.1 .
```
- you now created an own image which is installed locally on your machine. Look it up by typing ```docker images``` in your terminal. You should see it.
- Now run your own image:
```bash
docker run -d -p 5433:5432 --name myprj2database mydbimage:0.1
```
- Default username is `exam`
- Default password is `exam`
- Default database is `presidents` (having all the tables)

- now start this sample application locally with maven: ```mvn jetty:run```
- goto http://localhost:8080/webapi/myresource/presidents
- then goto http://localhost:8080/webapi/myresource/presidents/1

Et voilà, you have a running REST-Api Backend-example.

You can take this as a starting point for your project 2.


## Important

- You can use this as base for your own application. Note that you should put your SQL files into [sql-files](sql-files) folder, have a look into the already existing files (which are from databases 1 course). The databae will be created with your schema and filled with your data.
- If you change your *pre-filled* database sql files, you need to (re)-create your database image and e.g. give it a new version, e.g.:
```bash
docker build -t mydbimage:0.2 .
```

You can choose freely how you name you images (e.g. mydbimage or superimage) and the version tags (e.g. 0.1, 0.2, 1.0... )
Advise is to stick to normal version numbers.

- The java application has already the correct connection parameters set up for connecting with the database, but only if you did everything like described above. If you change the passwords in the Dockerfile, of course you need to adapt it.

- Note: it could be that you have to clean your application `mvn clean` or use ```mvn clean jetty:run```

**Have fun and feel free to change for your project purpose**

## Ideas

- if you understand fully what was done here, you can deploy as well the java application as war to docker if you like.
- further, you could use [Docker compose](https://docs.docker.com/compose/) for running all services with one command.

## Useful links and tutorials regarding REST-API with Jersey 2.x

- [getting-started-with-jersey2](https://psamsotha.github.io/jersey/2015/10/10/getting-started-with-jersey2.html)
- [create-rest-apis-with-jax-rs-2-0](https://restfulapi.net/create-rest-apis-with-jax-rs-2-0/)

## Optional, integrate Swagger UI
- [co-hosting-swagger-ui-with-your-jersey](https://medium.com/shark-bytes/co-hosting-swagger-ui-with-your-jersey-rest-api-using-maven-dependencies-44d88ae85bf8)
