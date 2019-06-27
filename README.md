# edu-spring-cloud

This is the sandbox project for microservice architecture.

Steps to run entirely set of applications:
1. build and run service-registry
1. (optional) go to http://localhost:8761/ to observe ran services
2. build and run config-server
3. build and run inventory-service
4. run MySQL 5.7 with "admin" password for root user and "catalog" MYSQL_DATABASE
5. build and run catalog-server
6. (optional) get hystrix statistics from http://localhost:8181/hystrix/monitor?stream=http%3A%2F%2Flocalhost%3A9001%2Factuator%2Fhystrix.stream%20
7.
