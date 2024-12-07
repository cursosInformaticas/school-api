package edu.school.app.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    private Meta meta;
    private T data;

    public ApiResponse(String status, String message, T data, long totalRecords, int page, int size) {
        this.meta = new Meta(status, message,totalRecords, page, size);
        this.data = data;
    }

    public ApiResponse(String status, String message, T data) {
        this.meta = new Meta(status, message);
        this.data = data;
    }
}
