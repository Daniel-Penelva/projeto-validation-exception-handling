package com.api.projetovalidationexceptionhandling.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")  // instrui o Lombok a gerar um método de fábrica estático chamado build do tipo UserRequest contendo todas as propriedades - exemplificando no UserService.
@NoArgsConstructor
public class UserRequest {
    
    @NotBlank
    @NotNull(message = "username shouldn`t be null")
    private String name;

    @Email(message = "invalid email address")
    private String email;

    @Pattern(regexp = "^\\d{11}$", message = "invalid mobile number entered")
    private String mobile;
    
    private String gender;

    @Min(18)
    @Max(50)
    private int age;

    @NotBlank
    private String nationality;
}
