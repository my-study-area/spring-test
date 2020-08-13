package br.com.spring.endpoint;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.spring.model.Student;
import br.com.spring.service.StudentService;


@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerIntegration {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StudentService service;

    @Test
    public void save() throws Exception {
      Student student = new Student("Zaphod", "zaphod@galaxy.net");
      mockMvc.perform(post("/students")
              .contentType("application/json")
              .content(objectMapper.writeValueAsString(student)))
              .andExpect(status().isOk());
      assertThat(service.findAll().size()).isEqualTo(1);
      Student studentFound = service.findByNameIgnoreCaseContaining("Zaphod").get(0);
      assertThat("zaphod@galaxy.net").isEqualTo(studentFound.getEmail());
    }
    
    
    @Test
    public void update() throws Exception {
        Student student = new Student("Zaphod", "zaphod@galaxy.net");
        
        service.save(student);
        student.setName("Adriano");

        mockMvc.perform(put("/students")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(student)))
        .andExpect(status().isOk());

        Optional<Student> studentFound = service.findById(student.getId());
        assertThat("Adriano").isEqualTo(studentFound.get().getName());
        service.delete(student);
    }
    
    @Test
    public void updateNotFound() throws Exception {
        Student student = new Student(999,"Zaphod", "zaphod@galaxy.net");
        
        mockMvc.perform(put("/students")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(student)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Student not found"));
    }
    
    
    @Test
    public void findById() throws Exception {
        Student student = new Student("Zaphod", "zaphod@galaxy.net");
        service.save(student);
        
        String id = student.getId().toString();
        MvcResult result= mockMvc
                .perform(get("/students/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        
        assertThat(result.getResponse()
                .getContentAsString())
                .isEqualTo("{\"id\":"+ id +",\"name\":\"Zaphod\",\"email\":\"zaphod@galaxy.net\"}");
        service.delete(student);
    }
    
    @Test
    public void findByIdNotFound() throws Exception {
       mockMvc.perform(get("/students/{id}", "999"))
              .andDo(print())
              .andExpect(status().isNotFound())
              .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Student not found"));
    }

}
