package io.enfuse.redisappdemo.azureCache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AzureRedisController {

    @Autowired
    private StudentService studentService;



    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents(){

        List<Student> studentsList = studentService.findAll();
        return ResponseEntity.ok(studentsList);
    }

    @GetMapping(value = "/students/{id}")
    public ResponseEntity<Object> getCustomerById(@PathVariable("id") String id) {

        Student student = this.studentService.findById(id);
        return ResponseEntity.ok(student);
    }
}


