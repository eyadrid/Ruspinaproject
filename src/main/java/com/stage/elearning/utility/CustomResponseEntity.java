package com.stage.elearning.utility;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CustomResponseEntity <T> {
    private HttpStatus status;
    private boolean statusString;
    private LocalDateTime timestamp;
    private T Data;

    public CustomResponseEntity(HttpStatus status, T data) {
        this.status = status;
        this.statusString = true;
        this.timestamp = LocalDateTime.now();
        Data = data;
    }
}
