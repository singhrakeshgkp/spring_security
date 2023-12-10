
# Spring Boot Security
# Table of Contents
- [Basic](#basic)
  - [001 Security Basic](#001-security-basic)-- Authentication, Authorization
 
# Basic
## 001 Security Basic
<p>Authentication and Authorization are security that we apply on our applications. Authentication is used to identify who u are? and authorization is used to v</p>

```
Role---> you are (you are admin, user, ... etc)
Authority--> have(you have authority to do this, that ...etc)
Encoding ----->Some rules that is used to transform the strinng in some output and then reverse it in original format. ex base64 encoding
Encryption-----> Input--->Transform to--->output, output---->Key/secret neeeded----->input. ex 
HashFunction---> Input---->output  output---to input not possible but it cas verify output if u have input.
                prefered way of storing password is hash function.
                
```
- Create a new spring boot application with web, security dependency, define a endpoint ```/security-test```, start the application, try to access the defined endpoint. Observe the response, it will be 401 unauthorized response.
- Provide the user name as ``user``` and password from intellij console to postman basic auth section. U should be able to access the endpoint.
   
