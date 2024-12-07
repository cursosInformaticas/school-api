package edu.school.app.config;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class Meta {
    private String status;
    private String message;
    private LocalDateTime timestamp;
    private long totalRecords;
    private int page;
    private int size;

    public Meta(String status, String message, long totalRecords, int page, int size) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.totalRecords = totalRecords;
        this.page = page;
        this.size = size;
    }

    public Meta(String status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}