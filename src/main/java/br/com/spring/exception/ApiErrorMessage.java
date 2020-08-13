package br.com.spring.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiErrorMessage {
    private String error;

    public ApiErrorMessage(String messageError) {
        this.error = messageError;
    }
}
