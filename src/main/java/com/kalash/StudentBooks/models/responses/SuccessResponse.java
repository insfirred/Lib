package com.kalash.StudentBooks.models.responses;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuccessResponse<T>{
    private int statusCode;
    private T data;
}
