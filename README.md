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
  2. Provide a RedisTemplate bean to take care of serialization of objects before caching them. We need to provide both key and value serializers.
     Redis will save all keys as strings so we are using a simple StringSerializer for the keys and GenericJackson2JsonRedisSerializer for our Student   objects.
  4. We also need to have our class Student be serializable so that Redis can transform the data coming in to instances of that class for our code to use.
  5. Service classes are the components dealing with the caching directly when reading from the database.
     We use annotation based caching that allows us to store records based on keys, as well as evicting those records when we update the database.

## Additional Notes:

**Redis CLI:**
Azure offers a way to interact with the cache server using the redis cli from the portal. 
Basic useful commands to check if data is being stored in the cache are:
  1. scan 0 - This will return all the different keys that are stored in the cache
  2. mget [key] - this will display the data associated with the key given

Resources Used

https://blog.katastros.com/a?ID=00700-3984d39a-992d-4b8c-b05f-512127bad5f9
https://docs.microsoft.com/en-us/azure/developer/java/spring-framework/configure-spring-boot-initializer-java-app-with-redis-cache
