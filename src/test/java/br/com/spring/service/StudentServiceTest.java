package br.com.spring.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.spring.model.Student;
import br.com.spring.repository.StudentRepository;

@SpringBootTest
public class StudentServiceTest {
    
    @MockBean
    private StudentRepository repository;
    
    @Autowired
    private StudentService service;

    @Test
    public void saveStudent() {
        //given
        Student student = new Student("Adriano", "adriano@email.com");
        given(repository.save(any(Student.class))).willReturn(studentReturned(1));
        
        //when
        Student studentService = service.save(student);
        
        //then
        assertThat(studentService.getId()).isEqualTo(1);
    }
    
    @Test
    public void updateStudent() {
        //given
        Student student = studentReturned(1);
        String nameUpdated = "Adriano alterado";
        student.setName(nameUpdated);
        given(repository.save(any(Student.class))).willReturn(student);
        
        //when
        Student studentService = service.save(student);
        
        //then
        assertThat(studentService.getId()).isEqualTo(1);
        assertThat(studentService.getName()).isEqualTo(nameUpdated);
    }
    
    @Test
    public void deleteStudent() {
        //given
        Student student = new Student("Adriano", "adriano@email.com");

        //when
        service.delete(student);
        
        //then
        Mockito.verify(repository, Mockito.times(1)).delete(student);
        
    }
    
    @Test
    public void getStudentById() {
        //given
        given(repository.findById(any(Integer.class))).willReturn(Optional.of(studentReturned(1)));
        
        //when
        Optional<Student> studentService = service.findById(1);
        
        //then
        assertThat(studentService.get().getId()).isEqualTo(1);
    }
    
    private Student studentReturned(int id) {
        return new Student(id, "adriano", "adriano@email.com");
    }
}
