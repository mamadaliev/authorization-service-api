[![Build Status](https://travis-ci.org/egnaf/auth-api-service.svg)](https://travis-ci.org/egnaf/auth-api-service)

# auth-api-service

- [Authorization](#Authorization)
- [Authentication](#Authentication)
- [Install](#Install)
- [Demo](#Demo)
- [Contribute](#Contribute)
- [License](#License)

## Install
To build the jar file, enter ``mvn clean install``, and to start the Spring Boot application, 
enter ``mvn spring-boot:run``.

```bash
$ mvn clean install
$ mvn spring-boot:run
```

## Authorization
Authorization is the function of specifying access rights/privileges to resources, which is related to information
security and computer security in general and to access control in particular. More formally, "to authorize" is
to define an access policy.

## Authentication
Authentication is the act of proving an assertion, such as the identity of a computer system userModel.
In contrast with identification, the act of indicating a person or thing's identity, authentication is 
the process of verifying that identity.

## Install
To build the jar file, enter ``mvn clean install``, and to start the Spring Boot application, 
enter ``mvn spring-boot:run``.

```bash
$ mvn clean install
$ mvn spring-boot:run
```

## Demo
After running application, visit this page: [127.0.0.1:8065](http://127.0.0.1:8065)
##### Registration
```text
curl -X POST http://localhost:8065/1.0/register
-d '{"username": "egnaf", "email": "egnaf@yahoo.com", "password": "12345678"}' 
-H "Content-Type: application/json"
```

##### Authentication
```text
curl -X POST http://localhost:8065/1.0/login
-d '{"username": "egnaf", "password": "12345678"}' 
-H {"Content-Type: application/json"}
```

##### List of userModels
```text
curl -X GET http://localhost:8065/1.0/userModels
-H {"Content-Type: application/json", "Authorization: Bearer {token}"}
```

## Contribute
For any problems, comments, or feedback please create an issue 
[here](https://github.com/egnaf/auth-api-service/issues).
<br>

## License
Project is released under the [MIT](https://en.wikipedia.org/wiki/MIT_License).
