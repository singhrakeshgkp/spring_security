
# Spring Boot Security
### Enabling Spring boot Security - SpringSecurity-1
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
  
 ### Get User Details from DB- SpringSecurity-2
 - Role based access
 - Configured allowed roles using expression based access control ``` @PreAuthorize("hasAnyAuthority('USER','ADMIN')") ```
 
### Configure Role based Authorization using request matcher - SpringSecurity-3
- ```requestMatchers("/greet","/hello")``` -> resource should be accessible by all(authenticated and unauthenticated) users
- ```.requestMatchers("/customer/**").hasAnyAuthority("ADMIN","USER")``` -> Should be accessible by user having Admin or user role
- ```.requestMatchers("/banklist/**").hasAnyAuthority("ADMIN")``` -> should be accessible by user having admin role only.
 
