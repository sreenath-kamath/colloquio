# Colloquio
 
|Item|Status|
|:----:|:------:|
|Master Build Status|[![Build Status](https://travis-ci.org/sreenath-kamath/colloquio.svg?branch=master)](https://travis-ci.org/sreenathkamath/colloquio)|
|Master Code Coverage|[![Coverage Status](https://coveralls.io/repos/github/sreenathkamath/colloquio/badge.svg?branch=master)](https://coveralls.io/github/sreenathkamath/colloquio?branch=master)|



How to start the colloquio application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/colloquio-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`
