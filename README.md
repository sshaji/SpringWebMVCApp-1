## web-spring-angular - WebSpringAngular
### Java Spring web application sample with :
* REST API
* Angular JS based GUI
* Simple Jsp UI
 
#### GUI URLs :
* Angular : http://server/applicationname/
* JSP : http://server/applicationname/server 

#### MySql Database details :
##### Database name : test

* Table : users
 - username (varchar - 60 - primary)
 - password (varchar - 60)
 - enabled (tinyint - 1)
 - firstname (varchar - 60)
 - lastname (varchar - 60)
 - email (varchar - 60)

* Table : authorities
 - username (varchar - 60 - primary - foreign key to users.username)
 - authorities (varchar - 60)
 
* Table : offers
 - id (int - primary - auto increment)
 - username (varchar - 60 - foreign key to users.username)
 - offerdetails (text)

#### Database Connection
* Default : JNDI / Hibernate
