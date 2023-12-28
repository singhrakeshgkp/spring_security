
# Spring Boot Security
# Table of Contents
- [Basic](#basic)
  - [001 Security Basic](#001-security-basic)--Authentication, Authorization. Authentication with gene pwd
  - [002 Security Basic](#002-security-basic) --Ahthentication with inmemory credential
  - [003 Security Basic](#003-security-basic) --Ahthentication with credential availabe in mysql
  - [Authority and Role Based Authentication](#authority-and-role-based-authentication)
    - [004 Security Basic](#004-security-basic) -- Authority based authentication
    - [005 Security Basic](#005-security-basic) -- Role and authority based authentication
  - [Customizing filter Authentication AM And AP](#customizing-filter-authentication-AM-And-AP)
     - [006 Security Basic](#006-security-basic) -- Configure own filter,AM,AP..etc
     - [007 Security Basic](#007-security-basic)-- Configure multiple filtere
  - [Authorization](#authorization)
    - [008 Security Basic](#008-security-basic) - endpoint based authorization
    - [009 Security Basic](#009-security-basic) - mehtod based authorization
- [Oauth2](/oauth2.md)  -- 010-security-oauth2      
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
  #### Role and authority based authorization -- Pending
# Customizing filter Authentication AM And AP
  ## 006 Security Basic
  #### Implement Custom Filter,AM(Authentication Manager),AP(Authentication, Provider) and Authenticate the request using secret from header instead of username and password.
  - Configure ```SecurityFilterChain``` bean in ```SecurityConfig``` class
  - Create new ```CustomAuthenticationFilter``` class and inject that bean in ```SecurityConfig``` class
  - Configure the ```CustomAuthenticationFilter``` at index of 
    ```UsernamePasswordAuthenticationFilter.class```
  - In custom filter class configure custom ```AM``` and then create Create ```CustomAuthentication and CustomAuthenticationProvider``` class.
  - run the applicationn and try to access ```/security-test``` resource without ```key``` header. You will get 403 forbidden error
  - Now pass the header and correct key value, this time u will be able to access the resource
  
  ## 007 Security Basic
  #### Configure ```ApiFilter apart from basic/default filter``` Authentifcation can be happen by either basic credential(user id+ pwd) or api key.
  - [diagram](/007-security-auth.png)
  - Testing
    - try to access ```/security-test``` endpoint with api key, u should be able to access the resource
    - Now remove api key and try to access same resource using user id and password, resource should be accessible here as well.
    - In case api key or user id or password is incorrect 403 unauthorized access response should be returned by server.
# Authorization      
## 008 Security Basic
#### After successfull authentication, the authentication details got stored into an object called ```SecurityContext```. Here we are going to implement endpoint based authorization

## 009 Security Basic
#### Method based authorization
- Use the annotation ```@EnableMethodSecurity``` to enable method based authorization
- By default ```AuthorizationFilter``` apply and request do not reach to controller, from this filter spring will throw 403 error, to disable this filter use below code in your configuration class
  ```
  .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
  ```
- Add ```@PreAuthorize("hasAuthority('read')")``` annotatin on your controller mehtod that u want to authorize.
