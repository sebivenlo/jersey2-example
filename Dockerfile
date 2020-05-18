FROM library/postgres
ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD mypassword
EXPOSE 5432
COPY sql-files/*.sql /docker-entrypoint-initdb.d/
