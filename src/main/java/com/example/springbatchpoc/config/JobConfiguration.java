package com.example.springbatchpoc.config;

import com.example.springbatchpoc.model.DataWrapper;
import com.example.springbatchpoc.model.OriginalData;
import com.example.springbatchpoc.processor.DataItemProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
@RequiredArgsConstructor
public class JobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job importUserJob(Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(ItemReader<OriginalData> itemReader,
            CompositeItemWriter<DataWrapper> writer,
            DataItemProcessor dataItemProcessor) {
        return stepBuilderFactory.get("step1")
                .<OriginalData, DataWrapper> chunk(5)
                .reader(itemReader)
                .processor(dataItemProcessor)
                .writer(writer)
                .build();
    }
}
