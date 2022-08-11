package io.enfuse.redisappdemo.azureCache;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@CacheConfig(cacheNames = "studentsCache")
public class StudentServiceImpl implements StudentService{
    @Autowired
    StudentRepository studentRepository;
    @Cacheable(cacheNames = "students")
    @Override
    public List<Student> findAll() {
        return  (List<Student>) studentRepository.findAll();
    }

    @Cacheable(cacheNames = "student", key="#id", unless="#result == null")
    @Override
    public Student findById(Long id) {
        Student student = studentRepository.findById(id).orElse(null);
        return student;
    }

    @CacheEvict(cacheNames = "student",key="#student.id")
    public Student updateStudent(Student student){
        Student update = studentRepository.findById(student.getId()).orElse(null);
        update.setFirstName(student.getFirstName());
        Student updated = studentRepository.save(update);
        return updated;
    }
}
