package io.enfuse.redisappdemo.azureCache;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
//@CacheConfig(cacheNames = "studentsCache")
public class StudentServiceImpl implements StudentService{

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    private RedisTemplate<Object, Object> studentTemplate;

//    @Cacheable(cacheNames = "students")
    @Override
    public List<Student> findAll() {
        String allStudentsKey = "allStudents";
        if(studentTemplate.hasKey(allStudentsKey)){
             ArrayList students = (ArrayList) studentTemplate.opsForValue().get(allStudentsKey);
             return students;
        }
        List<Student> students =  (List<Student>) studentRepository.findAll();
        studentTemplate.opsForValue().set(allStudentsKey,students);

        return students;
    }

//    @Cacheable(cacheNames = "student", key="id", unless="result == null")
    @Override
    public Student findById(String id) {

        if(studentTemplate.hasKey(id)){
            return (Student) studentTemplate.opsForValue().get(id);
        }
        Long _id = Long.valueOf(id);
        Student student = studentRepository.findById(_id).orElse(null);
        studentTemplate.opsForValue().set(id,student);

        return student;
    }
}
