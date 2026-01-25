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
<p> Any malicious user will be able to trick you and make request on your behalf.  </p>
