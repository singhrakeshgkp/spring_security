
# Spring Boot Security
# Table of Contents
- [Basic](#basic)
  - [001 Security Basic](#001-security-basic)--Authentication, Authorization. Authentication with gene pwd
  - [002 Security Basic](#002-security-basic) --Ahthentication with inmemory credential
  - [003 Security Basic](#003-security-basic) --Ahthentication with credential availabe in mysql
  - [Authority and Role Based Authentication](#authority-and-role-based-authentication)
    - [004 Security Basic](#004-security-basic) -- Authority based authentication
    - [005 Security Basic](#005-security-basic) -- Role and authority based authentication
 
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

## 002 Security Basic
- Create a security config class, in this class config user details service bean with inmemory cred and password encoder bean. test from postman with the inmemory cred.
- [Diagram](/security-basic/003-security-basic.png)
  - in the diagram filter implements HttpBasicAuthentication which delegates this to an object called AuthenticationManager, delegatates this(AuthMgr) to an object Called AuthenticationProvider.
  - And finally AuthenticationProvider use UserDetialsService and PasswordEncoder.
  - We have configured UserDetailsService and PasswordEncoder bean only other beans such as AuthenticationManager, AuthenticationProvider... etc preconfigured by spring boot.

## 003 Security Basic
- Add maria db, spring data jpa and lombok dependency. Configure the data source and table in maria db
- Comment the UserDetails bean defined in SecurityConfig file. Create entity, service and repo classess.
- run the application, try to access ```/security-test``` endpoint. after providing the credential u should be able to access the ```/security-test``` endpoint.

# Authority and Role Based Authentication
## 004 Security Basic
- Create ```authorities``` and ```customers_authorities``` tables and its corresponding entity, define ORM mappings.
- Crate ```CustomerAuthority``` Wrapper class and wrap authority in it.
- Add the following code in ```getAuthorities``` method of ```CustomerCredential.java``` class
  ```
   return customer.getAuthorities()
        .stream()
        .map(CustomerAuthority::new)
        .collect(Collectors.toList());
  ```

  ## 005 Security Basic
  ###### Role and authority based authorization -- Pending
  
  
