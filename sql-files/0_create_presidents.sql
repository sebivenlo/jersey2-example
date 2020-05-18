
DROP USER IF EXISTS exam;
DROP DATABASE IF EXISTS presidents;
CREATE USER EXAM password 'exam';
CREATE DATABASE presidents owner exam;
CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


