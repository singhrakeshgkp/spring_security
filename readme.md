
# Spring Boot Security
### Enabling Spring boot Security - SpringSecurity-1 using form based login
- Create new spring boot project with spring web, spring security, thymleaf and lombock dependencies.
- Create controller and try to access any resource, by default spring will redirect you on login page.
- Enter the defalt user name which is ```user``` and password from console(it is auto generated password)
- You can also give your own user name and password from properties file, for this you have to add below configuration in application.properties
  ```
  spring.security.user.name=rakesh
  spring.security.user.password=singh
  
<p><b>Configure InMemory Role based authentication </b></p>

  - Create new Security config class
  - configure user and its role. configure bean to encode the password
  - remove the user details given from properties file
  - Annotate the config class with ```@EnableMethodSecurity``` annotation
  - Configure role on top of your resource using ```@PreAuthorize("hasAuthority('ROLE_ADMIN')")``` annotation
  
 ### Get User Details from DB- SpringSecurity-2 using form based login
 - Role based access
 - Configured allowed roles using expression based access control ``` @PreAuthorize("hasAnyAuthority('USER','ADMIN')") ```
 
### Configure Role based Authorization using request matcher - SpringSecurity-3 using form based login
- ```requestMatchers("/greet","/hello")``` -> resource should be accessible by all(authenticated and unauthenticated) users
- ```.requestMatchers("/customer/**").hasAnyAuthority("ADMIN","USER")``` -> Should be accessible by user having Admin or user role
- ```.requestMatchers("/banklist/**").hasAnyAuthority("ADMIN")``` -> should be accessible by user having admin role only.
 
### Configure SSL - SpringSecurity-4 
- copyt springsecurity-3 proj
- Generate self signed certificate (on prod instead of using self signed cert buy it from trusted provider)
  - Go to your jdk bin installation directory and open command.
  - Run the below command from command prompt to generate the certificate.
    ```.\keytool -genkey -alias sslspring -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore sslspring.p12 -validity 3650```
  - copy the generated certificate to your application class path (resources folder). while generating the cert i used welcome123 pwd.
- Add following properties in application.properties file
  ```
  server.ssl.enabled=true
  server.ssl.key-store=src/main/resources/sslspring.p12
  server.ssl.key-store-password=welcome123
  server.ssl.key-store-type=PKCS12
  server.ssl.key-alias=sslspring

  ```
