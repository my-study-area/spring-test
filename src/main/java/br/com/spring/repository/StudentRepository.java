package br.com.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.spring.model.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, Integer>{
    List<Student> findByNameIgnoreCaseContaining(String name);
}
