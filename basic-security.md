# Table of Contents
- [CORS](/cors.md)
  - 00-sbsicsecurity-1-cors1  -->
  - 00-sbsicsecurity-1-cors2 -->
- [CSRF](/csrf.md)
  - 00-sbasicsecurity-2-csrf1 ---> Risk Without CSRF
  - 00-sbasicsecurity-2-csrf2 ---> Application with CSRF enabled
- [Authentication and Authorization](#Authentication-and-authorization)
  - [Authenticate user with InMemory credential](#Authenticate-user-with-InMemory-credential)


## Authentication and Authorization
- **Authentication** = Who are you? (Authentication is the process of verifying identity.)
- **Authorization** = What are you allowed to do? (Authorization is the process of checking permissions.)

```
Role---> you are (you are admin, user, ... etc)
Authority--> have(you have authority to do this, that ...etc)
Encoding ----->Some rules that is used to transform the strinng in some output and then reverse it in original format. ex base64 encoding
Encryption-----> Input--->Transform to--->output, output---->Key/secret neeeded----->input. ex 
HashFunction---> Input---->output  output---to input not possible but it cas verify output if u have input.
                prefered way of storing password is hash function.
                
```
### Default authentication 
- Create a new spring boot application with web, security dependency, define a endpoint ```/security-test```, start the application, try to access the defined endpoint. Observe the response, it will be 401 unauthorized response.
- Provide the user name as ``user``` and password from intellij console to postman basic auth section. U should be able to access the endpoint.
   

### Authenticate user with InMemory credential
#### Branch Name - 00-sbasicsecurity-3-authentication-1
- Create a security config class, in this class config user details service bean with inmemory cred and password encoder bean. test from postman with the inmemory cred.
- [Diagram](/diagram1.png)
  - in the diagram filter implements HttpBasicAuthentication which delegates this to an object called AuthenticationManager, delegatates this(AuthMgr) to an object Called AuthenticationProvider.
  - And finally AuthenticationProvider use UserDetialsService and PasswordEncoder.
  - We have configured UserDetailsService and PasswordEncoder bean only other beans such as AuthenticationManager, AuthenticationProvider... etc preconfigured by spring boot.
