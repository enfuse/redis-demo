package io.enfuse.redisappdemo.azureCache;

import org.springframework.stereotype.Service;

import java.util.List;

public interface StudentService {
    List<Student> findAll();

    Student findById(String id);
}
