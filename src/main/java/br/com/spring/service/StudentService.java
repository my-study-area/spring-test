package br.com.spring.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.com.spring.model.Student;
import br.com.spring.repository.StudentRepository;

@Service
@Component
public class StudentService {
    
    @Autowired
    private StudentRepository repository;
    
    public Student save(@Valid Student student) {
        return repository.save(student);
    }
    
    public void delete(Student student) {
        repository.delete(student);
    }
    
    public Optional<Student> findById(Integer id) {
        return repository.findById(id);
    }
    
    public List<Student> findByNameIgnoreCaseContaining(String name) {
        return repository.findByNameIgnoreCaseContaining(name);
    }

}
