package br.com.spring.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.spring.model.Student;
import br.com.spring.repository.StudentRepository;

@RestController
@RequestMapping(value = "/students")
public class StudentController {

    @Autowired
    private StudentRepository repository;
    
    @PostMapping
    public ResponseEntity<Student> save(@RequestBody Student student) {
        return new ResponseEntity<>(repository.save(student),HttpStatus.CREATED);
    }
}
