package com.api.projetovalidationexceptionhandling.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USERS_TBL")
@Data
@AllArgsConstructor(staticName = "build")  // instrui o Lombok a gerar um método de fábrica estático chamado build do tipo UserRequest contendo todas as propriedades - exemplificando no UserService.
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private int userId;
    
    private String name;
    private String email;
    private String mobile;
    private String gender;
    private int age;
    private String nationality;

}
