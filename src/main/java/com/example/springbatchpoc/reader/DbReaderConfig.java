package com.example.springbatchpoc.reader;

import com.example.springbatchpoc.model.OriginalData;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

@Configuration
public class DbReaderConfig {

    @Bean
    public ItemReader<OriginalData> itemReader(@Qualifier("originDataSource") DataSource dataSource,
            PagingQueryProvider queryProvider) {
        return new JdbcPagingItemReaderBuilder<OriginalData>()
                .name("pagingItemReader")
                .dataSource(dataSource)
                .pageSize(5)
                .queryProvider(queryProvider)
                .rowMapper(new BeanPropertyRowMapper<>(OriginalData.class))
                .build();
    }

    @Bean
    public SqlPagingQueryProviderFactoryBean queryProvider(@Qualifier("originDataSource") DataSource dataSource) {
        SqlPagingQueryProviderFactoryBean provider = new SqlPagingQueryProviderFactoryBean();
        provider.setDataSource(dataSource);
        provider.setSelectClause("SELECT id, name, email");
        provider.setFromClause("FROM original_data");
        provider.setSortKeys(sortByIdAsc());
        return provider;

        /**
         * @Bean
         *     @StepScope
         *     fun readQueryProvider() = SqlPagingQueryProviderFactoryBean().apply {
         *         setDataSource(dataSource)
         *         setSelectClause("select t1.table1_column1 as 't1.table1_column1', t1.table1_column2,"
         *                 + " t1.table1_column3, t2.table2_column2, t3.table3_column3")
         *         setFromClause("table1 t1"
         *                 + " inner join table2 t2 on t1.table1_column1 = t2.table2_column1"
         *                 + " inner join table3 t3 on t2.table2_column2 = t3.table3_column2"
         *                 + " inner join table4 t4 on t1.table1_column1 = t4.table4_column1")
         *         setWhereClause("where t3.table3_column2 = :placeholder1"
         * 				+ " and t2.table2_column3 = :placeholder2 and t4.table4_column2 = :placeholder3")
         *         setSortKey("t1.table1_column1")
         *     }
         *
         *      https://www.dineshonjava.com/spring-batch-read-from-csv-and-write-to-relational-db/
         *     https://github.com/spring-projects/spring-batch/issues/1208
         *     https://www.petrikainulainen.net/programming/spring-framework/spring-batch-tutorial-reading-information-from-a-database/
         */
    }

    private Map<String, Order> sortByIdAsc() {
        Map<String, Order> sortConfiguration = new HashMap<>();
        sortConfiguration.put("id", Order.ASCENDING);
        return sortConfiguration;
    }
};