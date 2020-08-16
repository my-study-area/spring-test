package br.com.spring.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @NotEmpty(message = "O campo nome do estudante é obrigatório")
    private String name;
    
    
    @NotEmpty(message = "O campo email do estudante é obrigatório")
    @Email(message = "O campo email está com formato inválido. Ex: email@email.com")
    private String email;


    public Student(String name, String email) {
        this.name = name;
        this.email = email;
    }
    
}
