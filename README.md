[![Build Status](https://travis-ci.org/egnaf/spring-boot-auth-jwt.svg)](https://travis-ci.org/egnaf/spring-boot-auth-jwt)

# spring-boot-auth-jwt

- [Authorization](#Authorization)
- [Authentication](#Authentication)
- [Install](#Install)
- [Demo](#Demo)
- [Contribute](#Contribute)
- [License](#License)

## Authorization
Authorization is the function of specifying access rights/privileges to resources, which is related to information
security and computer security in general and to access control in particular. More formally, "to authorize" is
to define an access policy.

## Authentication
Authentication is the act of proving an assertion, such as the identity of a computer system user.
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
curl -X POST http://127.0.0.1:8065/1.0/auth/register
-d '{"username": "demo", "email": "demo@demo.com", "password": "12345678"}' 
-H "Content-Type: application/json"
```

##### Authentication
```text
curl -X POST http://127.0.0.1:8065/1.0/auth/login
-d '{"username": "demo", "password": "12345678"}' 
-H {"Content-Type: application/json", "Authorization: Bearer {token}"}
```

##### List of users
```text
curl -X GET http://127.0.0.1:8065/1.0/users
-H {"Content-Type: application/json", "Authorization: Bearer {token}"}
```

## Contribute
For any problems, comments, or feedback please create an issue 
[here](https://github.com/egnaf/spring-boot-auth-jwt/issues).
<br>

## License
Project is released under the [MIT](https://en.wikipedia.org/wiki/MIT_License).
