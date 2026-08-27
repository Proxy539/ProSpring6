package com.apress.prospring6.thirteen;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record Letter(@JsonProperty("title")  String title,
                     @JsonProperty("sender") String sender,
                     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
                     @JsonProperty("sentOn")LocalDate sentOn,
                     @JsonProperty("content") String content) {
}
