[![Build Status](https://travis-ci.org/egnaf/auth-api-service.svg)](https://travis-ci.org/egnaf/auth-api-service)

# auth-api-service

- [Authorization](#Authorization)
- [Authentication](#Authentication)
- [Install](#Install)
- [Demo](#Demo)
- [Contribute](#Contribute)
- [License](#License)

## Install
To build the jar file, enter ``gradle build`` or to run the Spring Boot application, 
enter ``gradle bootRun``.

```bash
$ gradle build
$ gradle bootRun
```

## Authentication
Authentication is the act of proving an assertion, such as the identity of a computer system userModel.
In contrast with identification, the act of indicating a person or thing's identity, authentication is 
the process of verifying that identity.

## Authorization
Authorization is the function of specifying access rights/privileges to resources, which is related to information
security and computer security in general and to access control in particular. More formally, "to authorize" is
to define an access policy.

## Demo
After running application, visit this page: [127.0.0.1:8065/auth/0.1](http://127.0.0.1:8065/auth/0.1)
### 1. Registration
```text
curl -X POST http://localhost:8065/auth/0.1/register
-d '{"username": "egnaf", "email": "egnaf@yahoo.com", "password": "12345678"}' 
-H "Content-Type: application/json"
```

### 2. Authentication
```text
curl -X POST http://localhost:8065/auth/0.1/login
-d '{"username": "egnaf", "password": "12345678"}' 
-H {"Content-Type: application/json"}
```

### 3. List of users
```text
curl -X GET http://localhost:8065/auth/0.1/users?limit=5
-H {"Content-Type: application/json", "Authorization: Bearer {token}"}
```

## Contribute
For any problems, comments, or feedback please create an issue 
[here](https://github.com/egnaf/auth-api-service/issues).
<br>

## License
Project is released under the [MIT](https://en.wikipedia.org/wiki/MIT_License).
