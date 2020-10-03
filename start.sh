#!/bin/sh
./mvnw package
nohup java -jar target/demo-0.0.1-SNAPSHOT.war &