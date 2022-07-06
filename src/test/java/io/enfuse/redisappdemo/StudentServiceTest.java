package io.enfuse.redisappdemo;

import io.enfuse.redisappdemo.azureCache.Student;
import io.enfuse.redisappdemo.azureCache.StudentRepository;
import io.enfuse.redisappdemo.azureCache.StudentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentServiceTest {

    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    StudentService studentService;

    @Autowired
    CacheManager cacheManager;

    private static Student mockStudent;

    @BeforeAll
    public  static void setup(){
         mockStudent = new Student(1L, "testStudent");
    }
    @AfterEach
    public void clearCache(){
        cacheManager.getCacheNames().forEach(cacheName -> cacheManager.getCache(cacheName).clear());
    }

    @Test
    public void getStudent_shouldCacheStudent(){
        when(studentRepository.findById(1L)).thenReturn(Optional.of(mockStudent));

        Student cacheMiss = studentService.findById(1L);
        Student cacheHit = studentService.findById(1L);

        assertThat(cacheMiss.getFirstName()).isEqualTo(mockStudent.getFirstName());
        assertThat(cacheHit.getFirstName()).isEqualTo(mockStudent.getFirstName());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    public void patchStudent_shouldEvictFromCache(){
        Student mockStudent = new Student(1L, "testStudent");
        //cache record by calling findById
        when(studentRepository.findById(1L)).thenReturn(Optional.of(mockStudent));
        studentService.findById(1L);

        //update student
        mockStudent.setFirstName("updatedName");
        when(studentRepository.save(mockStudent)).thenReturn(mockStudent);
        Student updatedStudent = studentService.updateStudent(mockStudent);

        //verify repository's method vas invoked
        //notice that by now we have called findById 3 times (once inside service's updateStudent method)
        Student cacheMiss = studentService.findById(1L);

        verify(studentRepository, times(3)).findById(1L);
        assertThat(cacheMiss.getFirstName()).isEqualTo("updatedName");

    }
}
