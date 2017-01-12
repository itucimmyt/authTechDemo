# Technical Demo: LDAP Authentication
This demo shows a simple authentication process in Java. It connects to CIMMYT's Active Directory through an LDAP service and also supports authentication with a Google Account. It's designed to run as an executable _jar_ file.

This Project is based on the examples listed [here](#aGuide):

---
## What does it contain?
A minimal web application with two pages of interest:

1. Home: [http://localhost:8080/](http://localhost:8080/)
   * Anyone can see this page.
   * Displays a link for login, or the username if a session has started.
   * Logout button (if there's a session)
2. Secured: [http://localhost:8080/secured](http://localhost:8080/secured)
   * Only authenticated users can see this page.
   * If someone tries to access the page without a session, it will prompt for credentials.
   * Shows some information retrieved from authentication provider.
   * Logout button (if there's a session)

Application supports spanish and english(default). It uses the predefined language of the web browser.

## Requirements
* The project is built in Java 8 but should work well with 6 and 7 (no fully tested there).
* At runtime starts a local HTTP Server so it needs the port 8080 available.
* For authentication with Active Directory needs a LDAP server visible in the local network, or a public domain to access it.
    * It also needs an LDAP user with read privileges.
* For authentication with OAuth (Google) just needs to use the port 8080 and use _localhost_ as the server name, since this is the URI configured with the ITU account in Google Services when registering the app.

### Libraries
Most Relevant:

* Spring Boot: Autoconfigurations.
* Spring MVC: Dispatch web requests.
* Spring Security: Provide authentication and authorization capabilities.
* Spring Security LDAP: Sub-module for authentication with LDAP server.
* Spring Security OAuth2: Sub-module for authentication with OAuth2 authentication providers.
* Thymeleaf: Templating framework.
* Thymeleaf - Spring Security integration: Facilitates using Spring Security objects in templates with a custom _dialect_.

**NOTE**:
The project uses as much spring autoconfiguration features as possible to  minimize code and focus on the goal of this project: demonstrate authentication with Active Directory. In any way this prevents from using manual and more fine grained configurations.

---
## Build
You can build the project by using either Maven or Gradle. 

### Maven
```
$ mvn clean package
```
The executable _jar_ file will be found at *projectBaseDir*/target/adAuth-1.0.0.jar

### Gradle
```
$ gradle clean bootRepackage
```
The executable _jar_ file will be found at *projectBaseDir*/build/libs/adAuth-1.0.0.jar

Alternatively, a graddle wrapper can be used in case gradle is not installed in the host:
```
$ ./gradlew clean bootRepackage
```
---
## Run
Simply run it as a regular java executable:
```
$ java -jar adAuth-1.0.0.jar
```
You can specify a server port other than 8080 as:
```
$ java -jar adAuth-1.0.0.jar --server.port=9000
```

### Run from Code

#### Maven
```
$ SERVER_PORT=9000 mvn spring-boot:run
```

#### Gradle
```
$ SERVER_PORT=9000 gradle bootRun
```

Indenpendently of the building tool, press **CTRL+C** to stop execution. The port environment variable is optional.

The template-cache flag is disabled so changes in html and static files are hot-deployed.

---
## Further Material

### Reference
* [Spring Framework](http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/)
* [Spring Boot](http://docs.spring.io/spring-boot/docs/1.4.3.RELEASE/reference/htmlsingle/)
* [Spring Security](http://docs.spring.io/spring-security/site/docs/4.1.4.RELEASE/reference/html/)
* [Spring Security LDAP](http://docs.spring.io/spring-ldap/docs/2.2.0.RELEASE/reference/) (see chapter 12)
* [Thymeleaf Framework + Spring](http://www.thymeleaf.org/doc/tutorials/2.1/thymeleafspring.html)
* [Thymeleaf + Spring Security Integration](http://www.thymeleaf.org/doc/articles/springsecurity.html)

### <a name="aGuide"></a> Guides & Tutorials
* Guide: [Spring Boot with Security](http://docs.spring.io/spring-security/site/docs/current/guides/html5//helloworld-boot.html)
* Tutorial: [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* Guide: [Securing an existing Spring Application](http://docs.spring.io/spring-security/site/docs/current/guides/html5//helloworld-javaconfig.html)
* Guide: [OAuth 2 Developers Guide](http://projects.spring.io/spring-security-oauth/docs/oauth2.html)
* Tutorial: [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
