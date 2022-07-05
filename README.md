# redis-demo
Spring boot application that uses Azure Redis service to cache records of students.

Before running the application in local:

Spin up the docker container so that a postgress server instance is running in the backgroud.
The image will also create a database table of students with some records in it.
   
Steps taken to interact with Redis Service:
  1. Configure basic settings to talk to the service in application.properties file (DNS name, access key, port, SSL-enabled)
  2. Provide a RedisTemplate bean to take care of serialization of objects before caching them
  3. Service classes are the main players dealing with the caching directly before hitting the database.
     They use the RedisTemplate configured at bootup to save/retrieve records from the cache service.

Additional Notes
-Tried using annotation based caching but these were not caching the records to the cache. The records would always come directly from postgress.
-When caching a list of students for the getAllStudents method, currently the cache will not be aware of any new student added to the database. Possoble solution is to have an expiration timeout to have this evicted after some time. Or evict the key once we add/delete a new student.
