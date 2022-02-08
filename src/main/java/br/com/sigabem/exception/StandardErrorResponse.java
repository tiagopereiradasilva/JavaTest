package br.com.sigabem.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class StandardErrorResponse {
    private boolean erro;
    private Integer status;
    private List<String> messages;
    private String path;
    private Instant timestamp;

    public StandardErrorResponse(String message) {
        this.erro = true;
        this.messages = List.of(message);
    }

    public StandardErrorResponse(List<String> messages) {
        this.erro = true;
        this.messages = messages;
    }
}
