
## what is SSL/TLS
- SSL is a security protocol that creates an encrypted connection between a client (like web browser) and server (like your service/website). Think of it as a secure tunnel through which data travel safely

## The problem SSL solves
- when you send data over the network/internet without ssl
  - Anyone can read your data(request body like personal information, credit cards ...etc)
  - Can modify data in transit
  - you don't know if u are talking to the real server
 

## How SSL works 
- **Step 1 :-** User visit any any HTTPS website
- **Step 2 :-** Browser reach out to server
  - browser send a ```hello``` message, it says
    - i want to connect securely
    - I support these SSL/TLS version
    - I support these encryption method
- **Step 3 :-** Server respond with certificate
  - the server sends back ```hello``` response saying: - ok lets use this encryption method and TLS version
  - Server certificate--> this is like servers id card containing
    - server name
    - server public key
    - who issued this certificate
    - certificate expiry date
    - Digital signature from certificate authority
- **Step 4 :-** Browser verifies the certificate---> browser now acts like security guard checking id
  - check if its expired, reject if expired
  - It is for right website?
    - Lets say in certificate website is xyz.com
    - Browser is also connecting to xyz.com ---> no issue, connection/communication will happen smoothly
    - If in certificate its abc.com and you are visiting xyz.com, it will give warning
  - is it signed by trusted authority?
    - it verify digital signature of certificate
    - it proves certificate was not tempered
- **Step 5 :-**  Key exchange (The server part)
  - If all checks pass, the browser and server need to agree on a shared secret key for encryption data
  - Browser generates:
    - A random ```pre-master secret``` like a password
  - Browser encrypts this secret
    - Uses server's public key (from certificate)
    - Only server's private key can decrypt it
 - Browser sends
   - This encrypted secret to the server
 - Server decrypts:
   - Uses its private key (only server has this key)
   - Now both browser and server have the same secret
- **Step 6 :-** Secure connection established
  - Both create the same session keys from the shared secret. One key for encrypting messages from browser ----> server, one for encrypting messages from server----> browser
- **Step 7 :-** Secure data transfer
  - from now on. User submits login form----> encrypted before sending, server send back user data(encrypted before sending). Anyone intercepting sees gibberish
 
## How to check hello message from browser to server
1. visit ```chrome://net-export/``` click on start logging to disk
2. in another tab open any https website
3. now click on stop logging
4. Go to ```https://netlog-viewer.appspot.com```, choose logging file----> from left menu select events----> put ```SSL_HANDSHAKE_MESSAGE``` in top search box---> click on any entry and search hello text.



       
