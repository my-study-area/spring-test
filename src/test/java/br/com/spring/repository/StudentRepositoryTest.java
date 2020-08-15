package br.com.spring.repository;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test; //esse funciona com junit no eclipse
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.spring.model.Student;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StudentRepositoryTest {
     @Autowired
     private StudentRepository repository;

     @Autowired 
     private EntityManager entityManager;

    private Validator validator;

    @Before
    public void init() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }
    
    @Test
    public void createShouldPersisteData() {
        Student student = new Student("Adriano", "adriano@email.com");
        this.repository.save(student);
        Assertions.assertThat(student.getId()).isNotNull();
        Assertions.assertThat(student.getName()).isEqualTo("Adriano");
        Assertions.assertThat(student.getEmail())
                .isEqualTo("adriano@email.com");
    }

    @Test
    public void deleteShouldRemoveData() {
        Student student = new Student("Adriano", "adriano@email.com");
        this.repository.save(student);
        this.repository.delete(student);
        boolean isDeleted = repository.findById(student.getId()).isPresent();
        Assertions.assertThat(isDeleted).isEqualTo(false);
    }

    @Test
    public void updateShouldChangeData() {
        Student student = new Student("Adriano", "adriano@email.com");
        this.repository.save(student);
        student.setName("Adriano alterado");
        this.repository.save(student);
        Assertions.assertThat(student.getName()).isEqualTo("Adriano alterado");
    }

    @Test
    public void findByNameIgnoreCaseContainingShouldIgnoreCase() {
        Student student1 = new Student("Adriano", "adriano@email.com");
        Student student2 = new Student("adriano", "adriano2@email.com");
        this.repository.save(student1);
        this.repository.save(student2);
        List<Student> students = this.repository
                .findByNameIgnoreCaseContaining("Adriano");
        Assertions.assertThat(students.size()).isEqualTo(2);
    }

    @Test
    public void createShouldShowHasErrorFieldNameRequired() {
        Student student = new Student();
        student.setEmail("email@email.com");
        Set<ConstraintViolation<Student>> violations = this.validator.validate(student);
        Assertions.assertThat(violations.size()).isEqualTo(1);
        for (ConstraintViolation<Student> constraintViolation : violations) {
            Assertions.assertThat(constraintViolation.getMessage())
                      .isEqualTo("O campo nome do estudante é obrigatório");
        }
    }

    @Test
    public void createShouldShowHasErrorFieldEmailRequired() {
        Student student = new Student();
        student.setName("adriano");
        Set<ConstraintViolation<Student>> violations = this.validator.validate(student);
        Assertions.assertThat(violations.size()).isEqualTo(1);
        for (ConstraintViolation<Student> constraintViolation : violations) {
            Assertions.assertThat(constraintViolation.getMessage())
            .isEqualTo("O campo email do estudante é obrigatório").isInstanceOf(ConstraintViolation.class);
        }
    }
    
    @Test
    public void createShouldShowsErrorsWithNameFieldRequiredAndFieldEmailRequired() {
        Student student= new Student();

        final Throwable throwable = 
                Assertions.catchThrowable(
                    () -> {
                        repository.save(student);
                        entityManager.flush();
                });

        Assertions.assertThat(throwable)
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("O campo nome do estudante é obrigatório")
                .hasMessageContaining("O campo email do estudante é obrigatório");
    }

}
