# Table of content
- [CSRF](#CSRF)
  - [Spring security protects against CSRF](#Spring-security-protects-against-CSRF)
  - [When to use CSRF Protection](#When-to-use-CSRF-Protection)
  - [Why CSRF is enabled by default in spring?](#Why-CSRF-is-enabled-by-default-in-spring)
  - [What if CSRF is not enabled?](What-if-CSRF-is-not-enabled)


# CSRF
<p> CSRF stand for Cross Site Request Forgery, its type of attack where malicious website tricks a logged in user's browser and make the request without user realizing it.</p>
  
## Spring security protects against CSRF
<p> Spring security : </p>

- By Default csrf is enabled, Generate random token
- Stores it in session
- Expect every changing request(mutating request) such as POST,PUT,DELETE, PATCH include csrf token
- if token is missing or wrong u will get 403 forbidden error

## Why CSRF is enabled by default in spring?
<p> Because spring assumes that you are building browser app with cookes and session </p>
## When to use CSRF Protection
- CSRF works only when
  - Authentication stored in cookies
  - Browser sends cookies automatically
- CSRF doesn't work with
  - JWT
  - api key
  - basic auth 

## What if CSRF is not enabled?
<p> Any malicious user will be able to trick you and make request on your behalf. ex. you may get html form in email, image link etc, when you click on that it may trigger/send req. if u already logged in your browser automatically include session data this way some malicious user will be able to access your protect resource. </p>

### Demonstrate how malicious script/html can trick
#### Branch Name 00-sbasicsecurity-1-csrf1
- Create spring boot application with spring web, spring security, lombok and thymleaf dependencies
- Create SpringSecurityConfig class, disable csrf.
- create one controller and two methods /transfer-money Get Method and /transfer-money post endpoint.
- create transfer.html file in your project and malicious.html in outside project
- run application, login and try to transfer money. You will see ```transferred``` message on server console
- Now try to opne malicious.html in browser and submit form you will same with this form even you will be able to trafer money, to avoid this kind of valunerabilitity we can get rid by enabling csrf protection

### Demonstrate how csrf can restrict malicious script/html
#### Branch Name 00-sbasicsecurity-1-csrf2
- Now enable csrf
- include following hidden input in transfer.html file
  ```
    <input type="hidden"
         th:name="${_csrf.parameterName}"
         th:value="${_csrf.token}" />
  ```
- Now test using both file using inbuilt transfer.html and malicious.html file, you will see with inbuilt html u will get expected result while with malicious.html file u will get 403 forbidden error

