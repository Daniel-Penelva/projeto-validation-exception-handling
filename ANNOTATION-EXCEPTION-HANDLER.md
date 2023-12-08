# Classe de Exceção ApplicationExceptionHandler

A classe `ApplicationExceptionHandler` é um exemplo de um manipulador global de exceções em um aplicativo Spring Boot. 

```java
package com.api.projetovalidationexceptionhandling.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {

        Map<String, String> errorMap = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UserNotFoundException.class)
    public Map<String, String> handleBusinessException(UserNotFoundException ex){

        Map<String, String> errorMap = new HashMap<>();

        errorMap.put("errorMessage", ex.getMessage());
        return errorMap;
    }

}
```

## Método `handleInvalidArgument`

```java
@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {

        Map<String, String> errorMap = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }
```

Explicações das partes principais:

1. **`@RestControllerAdvice`**: Esta anotação é usada para indicar que a classe é responsável por manipular exceções em toda a aplicação. Funciona em conjunto com `@ExceptionHandler` e pode ser usada para centralizar a lógica de tratamento de exceções em um único lugar.

2. **`@ResponseStatus(HttpStatus.BAD_REQUEST)`**: Esta anotação define o código de status HTTP que será retornado quando a exceção for manipulada por este método. Neste caso, o código de status HTTP `400 Bad Request` será retornado. Isso indica que a requisição do cliente foi malformada ou inválida.

3. **`@ExceptionHandler(MethodArgumentNotValidException.class)`**: Esta anotação indica que o método `handleInvalidArgument` deve ser chamado quando uma exceção do tipo `MethodArgumentNotValidException` ocorrer. Essa exceção é geralmente lançada quando há falhas na validação dos argumentos de um método, por exemplo, em parâmetros de requisições.

4. **`public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex)`**: Este método é o manipulador de exceção para `MethodArgumentNotValidException`. Ele recebe a exceção como um parâmetro.

5. **`Map<String, String> errorMap = new HashMap<>();`**: Cria um `HashMap` para armazenar os detalhes do erro. Da mesma forma que no exemplo anterior, os erros de validação serão mapeados pelos nomes dos campos para as mensagens de erro associadas.

6. **`ex.getBindingResult().getFieldErrors().forEach(error -> {...});`**: A partir da exceção recebida, obtém o resultado de ligação (`BindingResult`) e, em seguida, obtém uma lista de erros de campo (`FieldError`). O método `forEach` é usado para iterar sobre cada erro de campo.

7. **`errorMap.put(error.getField(), error.getDefaultMessage());`**: Para cada erro de campo, adiciona uma entrada ao mapa de erros, onde a chave é o nome do campo e o valor é a mensagem padrão associada ao erro.

8. **`return errorMap;`**: Após construir o mapa de erros, o método retorna esse mapa. O Spring irá automaticamente serializar o mapa para JSON e incluí-lo na resposta HTTP.

Este controlador de exceções é útil para lidar com erros de validação globalmente em todo o aplicativo. Se ocorrer uma exceção do tipo `MethodArgumentNotValidException` em qualquer controlador do aplicativo, este manipulador será chamado para lidar com ela, retornando um mapa de erros no formato desejado. Isso ajuda a centralizar a lógica de tratamento de exceções relacionadas à validação.


## Método `handleBusinessException`

```java
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UserNotFoundException.class)
    public Map<String, String> handleBusinessException(UserNotFoundException ex){

        Map<String, String> errorMap = new HashMap<>();

        errorMap.put("errorMessage", ex.getMessage());
        return errorMap;
    }
```

Explicações das partes principais:

1. **`@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)`**: Esta anotação define o código de status HTTP que será retornado quando a exceção for manipulada por este método. Neste caso, `INTERNAL_SERVER_ERROR` (código 500) indica que ocorreu um erro interno no servidor. Isso geralmente é usado quando uma exceção não prevista ocorre e não é tratada de outra maneira.

2. **`@ExceptionHandler(UserNotFoundException.class)`**: Esta anotação indica que o método `handleBusinessException` será chamado quando uma exceção do tipo `UserNotFoundException` for lançada. `UserNotFoundException` provavelmente é uma exceção específica definida no aplicativo quando um usuário não é encontrado.

3. **`public Map<String, String> handleBusinessException(UserNotFoundException ex)`**: Este método é o manipulador de exceção para `UserNotFoundException`. Ele recebe a exceção como um parâmetro.

4. **`Map<String, String> errorMap = new HashMap<>();`**: Cria um `HashMap` para armazenar os detalhes do erro. Neste caso, um mapa simples é usado para armazenar a mensagem de erro associada à exceção.

5. **`errorMap.put("errorMessage", ex.getMessage());`**: Adiciona uma entrada ao mapa de erros, onde a chave é "errorMessage" e o valor é a mensagem da exceção (`ex.getMessage()`). Isso permite incluir informações específicas da exceção na resposta, o que pode ser útil para depuração.

6. **`return errorMap;`**: Após construir o mapa de erros, o método retorna esse mapa. Presumivelmente, este resultado seria serializado para JSON e enviado como resposta HTTP quando a exceção `UserNotFoundException` é lançada.

Este método é um exemplo de como personalizar o tratamento de exceções em uma aplicação Spring Boot. Ao definir o código de status HTTP e o conteúdo da resposta de erro, o desenvolvedor tem controle sobre como a aplicação responde quando uma exceção específica ocorre. Essa abordagem é útil para fornecer feedback significativo ao cliente ou ao consumidor da API sobre o que deu errado.

## Lógica de tratamento de exceção quando NÃO encontra usuário por id.

Analisando a lógica de tratamento de exceções para buscar usuário por id e como eles estão interligados:

1. **Classe `UserNotFoundException`:**
   ```java
   public class UserNotFoundException extends Exception {

       public UserNotFoundException(String message) {
           super(message);
       }
   }
   ```
   - Esta classe estende a classe `Exception` e é usada para criar uma exceção personalizada chamada `UserNotFoundException`. Essa exceção é lançada quando um usuário não é encontrado no método `getUser` da classe de serviço.

2. **Método `getUser` na classe de serviço (`UserService`):**
   ```java
   public User getUser(int id) throws UserNotFoundException{
       User user = userRepository.findByUserId(id);

       if(user != null){
           return user;
       } else {
           throw new UserNotFoundException("user not found with id: " + id);
       }
   }
   ```
   - Este método tenta buscar um usuário no repositório (`userRepository`) com base no ID fornecido.
   - Se o usuário for encontrado, ele é retornado.
   - Se o usuário não for encontrado, uma exceção `UserNotFoundException` é lançada com uma mensagem indicando que o usuário não foi encontrado.

3. **Método `getUser` no controlador (`UserController`):**
   ```java
   @GetMapping("/search/{id}")
   public ResponseEntity<User> getUser(@PathVariable int id) throws UserNotFoundException{
       return ResponseEntity.ok(userService.getUser(id));
   }
   ```
   - Este método é um endpoint HTTP que lida com solicitações GET na URL `/search/{id}`.
   - Ele chama o método `getUser` do serviço (`userService`) para obter um usuário com o ID fornecido.
   - Se o usuário for encontrado, ele é retornado no corpo da resposta HTTP. Se não for encontrado, a exceção `UserNotFoundException` é propagada para o manipulador global de exceções.

4. **Classe `ApplicationExceptionHandler` (Controlador de Exceções Global):**
   ```java
   @RestControllerAdvice
   public class ApplicationExceptionHandler {

       @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
       @ExceptionHandler(UserNotFoundException.class)
       public Map<String, String> handleBusinessException(UserNotFoundException ex){

           Map<String, String> errorMap = new HashMap<>();

           errorMap.put("errorMessage", ex.getMessage());
           return errorMap;
       }
   }
   ```
   - Esta classe é um manipulador global de exceções (`@RestControllerAdvice`) e especificamente trata exceções do tipo `UserNotFoundException`.
   - Quando a exceção é capturada, ela configura a resposta HTTP com um status `INTERNAL_SERVER_ERROR` e retorna um mapa contendo a mensagem de erro da exceção.

Em resumo, se o método `getUser` na classe de serviço não encontrar um usuário, ele lançará uma exceção `UserNotFoundException`. Essa exceção é então propagada até o controlador, onde o manipulador global de exceções (`ApplicationExceptionHandler`) lida com ela, configurando a resposta HTTP apropriada com um status `INTERNAL_SERVER_ERROR` e retornando um mapa contendo a mensagem de erro. Essa abordagem permite centralizar o tratamento de exceções em um local específico na aplicação.

# Autor
## Feito por: `Daniel Penelva de Andrade`