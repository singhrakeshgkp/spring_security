# Table of Contents
- [CORS](#CORS)

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
- For dangerous operation (Post, Put, Delete) Browser first sends following data
  ```
  OPTIONS /api/data
  Origin: https://app.company.com

  ```
- Server replies
  ```
  Access-Control-Allow-Methods: POST
  Access-Control-Allow-Headers: Content-Type

  ```
  only then real request is sent
