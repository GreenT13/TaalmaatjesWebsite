# Taalmaatjes website
This repository is based on the 'Taalmaatjes' repository, which is a JavaFX desktop application. It is now transformed into a full fledged website!

## Setup
This website has two parts: the REST API for the backend and Angular as frontend. It is all build into a war that is deployed on a tomcat, where the production build of Angular (content of dist folder) is put into webapp. Every url starting with /api is mapped to the RESTEasy servlet.

Every URL starting with /api is secured with basic authentication. Angular uses its own managing of accessible URL's.

### Frameworks frontend
I believe Angular 2.

### Frameworks backend
This website uses the following frameworks:
- RESTEasy (API handeling)
- jOOQ (ORM)
- Apache Shiro (security)
- JBCrypt (securing passwords)
- Log4j (with slf4j-log4j adapter)
- Guice (MethodInterceptor)
