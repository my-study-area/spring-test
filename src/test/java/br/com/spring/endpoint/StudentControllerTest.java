package br.com.spring.endpoint;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.WebApplicationContext;

import br.com.spring.SpringTestsApplication;
import br.com.spring.model.Student;
import br.com.spring.service.StudentService;


@SpringBootTest(classes = SpringTestsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentControllerTest {
    
    private static final String URI = "/students";

    @Autowired
    WebApplicationContext webApplicationContext;
    
    @MockBean
    private StudentService service;
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void createShouldPersistDataAndReturnStatusCodeCreated() throws Exception{
        Student student = new Student(3, "Sam", "sam@lotr.com");
        BDDMockito.when(service.save(student)).thenReturn(student);
        ResponseEntity<Student> response = restTemplate.postForEntity(URI, student, Student.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(response.getBody().getId()).isNotNull();
    }
    
}
