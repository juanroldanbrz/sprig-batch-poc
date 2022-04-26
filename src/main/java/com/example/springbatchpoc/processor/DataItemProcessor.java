package com.example.springbatchpoc.processor;

import com.example.springbatchpoc.model.DataWrapper;
import com.example.springbatchpoc.model.MappedData;
import com.example.springbatchpoc.model.OriginalData;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataItemProcessor implements ItemProcessor<OriginalData, DataWrapper> {

    @Override
    public DataWrapper process(OriginalData originalData) {
        var mappedData = new MappedData(originalData.getId(),
                originalData.getName().toUpperCase(Locale.ROOT),
                originalData.getEmail().toUpperCase(Locale.ROOT));
        log.info("Mapped item");
        return new DataWrapper(originalData, mappedData);
    }
}
