package com.example.freelancer.resdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseAPI<T> {
    private T data;
    private String message;
    private int code;
    private int status;

    public ResponseAPI(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public ResponseAPI(T data, String message, int code) {
        this.data = data;
        this.message = message;
        this.code = code;
    }
}
