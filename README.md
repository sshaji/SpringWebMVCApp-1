## web-spring-angular - WebSpringAngular
### Java Spring web application sample with :
* REST API
* Angular JS based GUI
* Simple Jsp UI
 
#### GUI URLs :
* Angular : http://server/applicationname/
* JSP : http://server/applicationname/server 

#### MongoDB Database details :
##### Database name : offers

* Collection : users
 - _id (primary)
 - password 
 - enabled
 - firstName
 - lastName
 - email
 
* Collection : offers
 - _id (primary)
 - user (DbRef to users)
 - offerDetails
