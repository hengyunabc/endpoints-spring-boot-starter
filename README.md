
## endpoints-spring-boot-starter

Show all spring boot endpoints mappings url.


Search the latest version in the cnetral repository : [Search](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22io.github.hengyunabc%22%20AND%20a%3A%22endpoints-spring-boot-starter%22)

```xml
<dependency>
    <groupId>io.github.hengyunabc</groupId>
    <artifactId>endpoints-spring-boot-starter</artifactId>
    <version>$version</version>
</dependency>
```

## spring boot 1

application.properties:

```
management.port=8081
management.security.enabled=false
```

urls:

* http://localhost:8081/endpoints
* http://localhost:8081/endpoints/mappings

## spring boot 2

application.properties:

```
management.server.port=8081
management.endpoints.web.exposure.include=*
```

urls:

* http://localhost:8081/actuator/endpoints
* http://localhost:8081/actuator/endpoints/mappings
