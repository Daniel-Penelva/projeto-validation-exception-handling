package com.api.projetovalidationexceptionhandling.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")  // instrui o Lombok a gerar um método de fábrica estático chamado build do tipo UserRequest contendo todas as propriedades.
@NoArgsConstructor
public class UserRequest {
    
    private String name;
    private String email;
    private String mobile;
    private String gender;
    private int age;
    private String nationality;
}
