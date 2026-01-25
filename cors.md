# Table Of contents
- [CORS](#CORS)
  - [When you will not get cors error](#When-you-will-not-get-cors-error)
  - [Why CORS is required?](#Why-CORS-is-required?)


## CORS
### CORS - Stand for Cross Origin Request Sharing
- CORS is a security feature implemented by browsers.
- CORS allow web application running at one origin to request resources from another origin.
- CORS protects User Not Server

## When you will not get cors error
- **Same Origin** --> Web app and api share exact same domain, protocol and port.
- **Direct Browser access** --> if you paste api url directly into browsers address bar.
- **Not Browsers event** --> If you use tools like postman, cURL ...etc

## Why CORS is required?
- Lets say you logged into abcbank.com in one tab.
- you open malicious site such as evil.com in another tab
- evil.com runs java script ```fetch('https://abcbank.com/api/account-details')
- Since your browser automatically sends cookies, session tokens. The response would contains balance info, txn history and many more information. Evil.com can read and send these information to hackers and this would be a massive security disaster.

## SOP (Same Origin Policy)
- Before CORS browsers already had SOP meaning a web page can only read responses from same origin (origin = Protocol + Domain + Port)

## Preflight Requests (Options)
- A preflight options request happens when browser thinks request is non simple
- **Conditions for preflight request**
  - Method is not Get, Post, Head
  - OR Content Type is not one of
     - text/plain
     - x-www-form-urlencoded
     - multipart/form-data
  - OR Request includes custom header The browser decides before sending the real request whether it needs a preflight.
  ```
  OPTIONS /api/data
  Origin: https://app.company.com
  ```
- Server replies
  ```
  Access-Control-Allow-Methods: POST
  Access-Control-Allow-Headers: Content-Type

  ```
  only then the real request is sent



  ## Implementation
  - We can define Cors at controller level or In central place such as in some config file.
  ### Without Spring boot security (Branch name  '00-sbsicsecurity-1-cors1)
  - Create a simple spring boot application running on port 8181
  - Create a get endpoint (/test), 
  - Try to access above endpoint from any web application, for testing we have created web-uiapp react application running on port 5173. Using this application try to access ```/test``` or any other endpoint.
  - If u see browser console you will see ```Access to XMLHttpRequest at 'http://localhost:8181/test' from origin 'http://localhost:5173' has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.``` error.
  - To fix above issue, Create configuration class ```CorsConfig``` and define one bean in it.
  - Run application and check network in your browser you should be able to see response header very similar as shown below.
    ```
    access-control-allow-credentials true
    access-control-allow-origin http://localhost:5173
    connection keep-alive
    content-length 47
    content-type application/json
    date Thu, 22 Jan 2026 22:21:10 GMT
    keep-alive timeout=60
    vary Origin
    vary Access-Control-Request-Method
    vary Access-Control-Request-Headers
    ```
  ### With Spring boot security (Branch name  '00-sbsicsecurity-1-cors2)
  - Add Spring Boot security dependency
  - Create new class SpringSecurityConfig and define SecurityFilterChain Bean and CorsConfigurationSource Bean.
  - **Note:- ** With spring boot 4.0.2 i was getting cors issue in prefliht request, solution is to name your cors bean method ```corsConfigurationSource``` it worked. Previously i had used ```configurationSource``` with this method name it did not worked as request for not reaching to till cors, spring security was rejecting it.
  - Now while testing make sure if you are doing following thing
    - http.httpBasic(Customizer.withDefaults()); is present in your security filter if testing with basic authentication
    - you are passing updaed password, here we have used basic auth and default username ```user``` and passowrd copied from idea intellij console.

  ### Understanding Property what we configured in CorsConfig
    ``` registry.addMapping("/**")  ----> the cors config apply for which resource(/**) all resource, (/abc) applicable for abc endpoint only if u try to access         /test you will get error localhost:7173 has been blocked by Cors policy.
       .allowedOrigins("http://localhost:5173/") ---> List of origin which is allowed
       .allowedMethods("GET","POST")   ---> List of allowed method
       .allowedHeaders("*") ---> allowed all headers in request, was it .allowedHeaders("X-Custom-Header"), you could pass only X-Custom-Headers + Default Header what browsers passes like content type, ....etc
       .allowCredentials(true);  ---> Browser is allowed to include credentials in cross origin requests.
                                      here credentials => Cookies, Http Authentication Headers, Client TLS Certificate....etc anything that identify user session
    ```


## Curls
1. To test preflight
   ```
    curl -i -X OPTIONS http://localhost:8181/abc \
     -H "Origin: http://localhost:5173" \
     -H "Access-Control-Request-Method: GET"
   ```

2. To test api
```

curl 'http://localhost:8181/abc' \
  -H 'Accept: application/json, text/plain, */*' \
  -H 'Accept-Language: en-US,en;q=0.9' \
  -H 'Authorization: Basic dXNlcjowMWM2MWYyNi05YzNkLTRkNTktYjk5Ny1kNmM4MzA3MzVlNmU=' \
  -H 'Connection: keep-alive' \
  -b '__next_hmr_refresh_hash__=186; JSESSIONID=ABC2A6F3861097BEB52EF7DABE2E2206' \
  -H 'Origin: http://localhost:5173' \
  -H 'Referer: http://localhost:5173/' \
  -H 'Sec-Fetch-Dest: empty' \
  -H 'Sec-Fetch-Mode: cors' \
  -H 'Sec-Fetch-Site: same-site' \
  -H 'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/142.0.0.0 Safari/537.36' \
  -H 'X-Custom-test-Header: my-custom-value' \
  -H 'sec-ch-ua: "Chromium";v="142", "Google Chrome";v="142", "Not_A Brand";v="99"' \
  -H 'sec-ch-ua-mobile: ?0' \
  -H 'sec-ch-ua-platform: "macOS"'
```

## Understanding response Headers

- Vary: Origin → cache must not share this response across different frontend domains
- Vary: Access-Control-Request-Method → cache must not share response for different methods
- Vary: Access-Control-Request-Headers → cache must not share response if different custo
