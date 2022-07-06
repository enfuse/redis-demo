# redis-demo
Spring boot application that uses Azure Redis service to cache records of students.

Before running the application in local:

**Spin up the docker container:**
Navigate into the postgress folder at the root of the project and run the command  
  ```docker compose up```    
This will boot up postgress at port **5432**
The image will also create a database table of **students** with some sample records in it inside the default postgress database.
   
**Steps taken to interact with Redis Service:**
  1. Inside application.properties, configure all the settings to talk to a Azure Redis Service instance
  2. Provide a RedisTemplate bean to take care of serialization of objects before caching them. Need to provide both key and value serializers.
     Redis will save all keys as strings so I used a basic StringSerializer for the keys and GenericJackson2JsonRedisSerializer for the values.
  4. Service classes are the main players dealing with the caching directly before hitting the database.
     They use the RedisTemplate configured at bootup to save/retrieve records from the cache service.

## Additional Notes:

**Redis CLI:**
Azure offers a way to interact with the cache server using the redis cli from the portal. 
Basic useful commands to check if data is being stored in the cache are:
  1. scan 0 - This will return all the different keys that are stored in the cache
  2. mget [key] - this will display the data associated with the key given

Resources Used

https://blog.katastros.com/a?ID=00700-3984d39a-992d-4b8c-b05f-512127bad5f9
https://docs.microsoft.com/en-us/azure/developer/java/spring-framework/configure-spring-boot-initializer-java-app-with-redis-cache
