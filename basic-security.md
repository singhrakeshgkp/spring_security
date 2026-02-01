# Table of Contents
- [CORS](/cors.md)
  - 00-sbsicsecurity-1-cors1  -->
  - 00-sbsicsecurity-1-cors2 -->
- [CSRF](/csrf.md)
  - 00-sbasicsecurity-2-csrf1 ---> Risk Without CSRF
  - 00-sbasicsecurity-2-csrf2 ---> Application with CSRF enabled
- [Authentication and Authorization](#Authentication-and-authorization)
  - [Authenticate user with InMemory credential](#Authenticate-user-with-InMemory-credential)
  - [Authenticate user with DB Credential](#Authenticate-user-with-db-credential)
  - [Authority Based Authentication](#authority-based-authentication)
  - [Role and authority based authorization](Role-and-authority-based-authorization)
  - [Custom Filter, AM,AP,](#custom-filter)


# Authentication and Authorization
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
   

## Authenticate user with InMemory credential
#### Branch Name - 00-sbasicsecurity-3-authentication-1
- Create a security config class, in this class config user details service bean with inmemory cred and password encoder bean. test from postman with the inmemory cred.
- Url to test from terminal ```curl -u test:test123 http://localhost:8181/greet```
- [Diagram](/diagram1.png)
  - in the diagram filter implements HttpBasicAuthentication which delegates this to an object called AuthenticationManager, delegatates this(AuthMgr) to an object Called AuthenticationProvider.
  - And finally AuthenticationProvider use UserDetialsService and PasswordEncoder.
  - We have configured UserDetailsService and PasswordEncoder bean only other beans such as AuthenticationManager, AuthenticationProvider... etc preconfigured by spring boot.
 
## Authenticate user with DB Credential
#### Branch Name - 00-sbasicsecurity-3-authentication-2
- Add maria db, spring data jpa and lombok dependency. Configure the data source and table in maria db
- Comment the UserDetails bean defined in SecurityConfig file. Create entity, service and repo classess.
- run the application, try to access ```/test``` endpoint. after providing the credential u should be able to access the ```/test``` endpoint.

## Authority Based Authentication
### Branch Name - 00-sbasicsecurity-3-authentication-3
- Create ```authorities``` and ```customers_authorities``` tables and its corresponding entity, define ORM mappings.
- Crate ```CustomerAuthority``` Wrapper class and wrap authority in it.
- Add the following code in ```getAuthorities``` method of ```CustomerCredential.java``` class
  ```
   return customer.getAuthorities()
        .stream()
        .map(CustomerAuthority::new)
        .collect(Collectors.toList());
  ```
## Role and authority based authorization
### Branch Name - 00-sbasicsecurity-3-authentication-4  -----pending



## Custom Filter
- [diagram](/filter.png)
### Branch Name - 00-sbasicsecurity-4-filter-1
<p> 
  Implement Custom Filter,AM(Authentication Manager),AP(Authentication, Provider) and Authenticate the request using secret from header instead of username and password. 
</p>

  - Configure ```SecurityFilterChain``` bean in ```SecurityConfig``` class
  - Create new ```CustomAuthenticationFilter``` class and inject that bean in ```SecurityConfig``` class
  - Configure the ```CustomAuthenticationFilter``` at index of 
    ```UsernamePasswordAuthenticationFilter.class```
  - In custom filter class configure custom ```AM``` and then create Create ```CustomAuthentication and CustomAuthenticationProvider``` class.
  - run the applicationn and try to access ```/security-test``` resource without ```key``` header. You will get 403 forbidden error
  - Now pass the header and correct key value, this time u will be able to access the resource
