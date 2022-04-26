package com.example.springbatchpoc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MappedData {

    private Integer originalId;
    private String normalizedName;
    private String normalizedEmail;

}
