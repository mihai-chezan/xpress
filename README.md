xpress
======

1. Build
mvn clean install

2. Run
java -jar target/xpress-0.0.1-SNAPSHOT.jar server config.yml

3. Vote

curl -X POST -H "Content-Type: application/json" -d '{"mood":"HAPPY","tag":"cougar"}' http://localhost:9090/service/vote
