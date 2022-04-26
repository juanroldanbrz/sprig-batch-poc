package com.example.springbatchpoc.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DataWrapper {

    private final OriginalData originalData;
    private final MappedData mappedData;

}
