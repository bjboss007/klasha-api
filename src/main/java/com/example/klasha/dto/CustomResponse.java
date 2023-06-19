package com.example.klasha.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomResponse {
    private boolean status;
    private int code;
    private String message;
    private ErrorType type;
    private List<String> errors;
    private Object data;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private String timestamp;

    public void setTimestamp(Date date) {
        if (date == null) {
            this.timestamp = "";
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.timestamp = dateFormat.format(date);
    }
}
