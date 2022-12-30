package com.example.hospitalproject.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class Response {
    private int code;
    private boolean success;
    private Result result;

    public Response success(){
        return new Response(0, true, null);
    }

    public <T> Response success(T data){
        return new Response(0, true, new Success<>(data));
    }

    public Response failure(int code, String msg){
        return new Response(code, false, new Failure(msg));
    }
}
