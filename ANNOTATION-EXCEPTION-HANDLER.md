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