package com.example.springbatchpoc.writer;

import com.example.springbatchpoc.model.DataWrapper;
import java.util.Arrays;
import javax.sql.DataSource;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class ItemWriterConfig {

    private static final String QUERY_INSERT_CLONED_DATA =
            "INSERT " + "INTO cloned_data(id, name, email) "
                    + "VALUES (:originalData.id, :originalData.name, :originalData.email) "
                    + "on duplicate key update id=:mappedData.originalId";

    private static final String QUERY_INSERT_MAPPED_DATA =
            "INSERT " + "INTO mapped_data(original_id, normalized_name, normalized_email) "
                    + "VALUES (:mappedData.originalId, :mappedData.normalizedName, :mappedData.normalizedEmail) "
                    + "on duplicate key update original_id=:mappedData.originalId";

    @Bean
    ItemWriter<DataWrapper> originalDataItemWriter(DataSource dataSource,
            NamedParameterJdbcTemplate jdbcTemplate) {

        JdbcBatchItemWriter<DataWrapper> databaseItemWriter = createBaseWriter(dataSource, jdbcTemplate);
        databaseItemWriter.setSql(QUERY_INSERT_CLONED_DATA);
        return databaseItemWriter;
    }

    @Bean
    ItemWriter<DataWrapper> mappedDataItemWriter(DataSource dataSource, NamedParameterJdbcTemplate jdbcTemplate) {

        JdbcBatchItemWriter<DataWrapper> databaseItemWriter = createBaseWriter(dataSource, jdbcTemplate);
        databaseItemWriter.setSql(QUERY_INSERT_MAPPED_DATA);
        return databaseItemWriter;
    }

    private JdbcBatchItemWriter<DataWrapper> createBaseWriter(DataSource dataSource,
            NamedParameterJdbcTemplate jdbcTemplate) {

        JdbcBatchItemWriter<DataWrapper> databaseItemWriter = new JdbcBatchItemWriter<>();
        databaseItemWriter.setDataSource(dataSource);
        databaseItemWriter.setJdbcTemplate(jdbcTemplate);
        ItemSqlParameterSourceProvider<DataWrapper> paramProvider = new BeanPropertyItemSqlParameterSourceProvider<>();
        databaseItemWriter.setItemSqlParameterSourceProvider(paramProvider);
        return databaseItemWriter;
    }

    @Bean
    public CompositeItemWriter<DataWrapper> compositeItemWriter(ItemWriter<DataWrapper> originalDataItemWriter,
            ItemWriter<DataWrapper> mappedDataItemWriter) {
        CompositeItemWriter<DataWrapper> writer = new CompositeItemWriter<>();
        writer.setDelegates(Arrays.asList(originalDataItemWriter, mappedDataItemWriter));
        return writer;
    }
}
