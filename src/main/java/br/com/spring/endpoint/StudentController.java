package br.com.spring.endpoint;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.spring.exception.StudentNotFound;
import br.com.spring.model.Student;
import br.com.spring.service.StudentService;

@RestController
@RequestMapping(value = "/students")
public class StudentController {

    @Autowired
    private StudentService service;
    
    @PostMapping
    public ResponseEntity<Student> save(@RequestBody Student student) {
        return new ResponseEntity<>(service.save(student),HttpStatus.OK);
    }
    
    @PutMapping
    public ResponseEntity<Student> update(@Valid @RequestBody Student student) {
        return new ResponseEntity<>(service.update(student),HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Student> findById(@PathVariable("id") Integer id) {
        Optional<Student> studentFound = service.findById(id);
        return new ResponseEntity<>(studentFound.orElseThrow(() -> new StudentNotFound("Student not found")),HttpStatus.OK);
    }
}
