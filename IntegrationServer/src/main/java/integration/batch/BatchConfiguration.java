package integration.batch;

import integration.model.FileEntry;
import integration.model.OutputEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

        private static final Logger LOGGER = LoggerFactory.getLogger(BatchConfiguration.class);

        @Autowired
        private JobBuilderFactory jobBuilderFactory;

        @Autowired
        private StepBuilderFactory stepBuilderFactory;

        @Value("${file.input}")
        private Resource fileInput;

        @Value("${file.output}")
        private String fileOutput;

        @Value("${file.output.path}")
        private String filePath;

        @Value("${file.chunk}")
        private Integer chunk;

        @Bean
        public FlatFileItemReader<FileEntry> reader() {
                LOGGER.info("create reader called");
                FlatFileItemReader<FileEntry> reader = new FlatFileItemReader<>();
                reader.setResource(fileInput);
                reader.setLinesToSkip(1);
                reader.setLineMapper(new DefaultLineMapper<FileEntry>() {{
                        setLineTokenizer(new DelimitedLineTokenizer() {{
                                setNames("id", "first_name", "last_name", "email",
                                        "supplier_pid", "credit_card_number",
                                        "credit_card_type", "order_id", "product_pid",
                                        "shipping_address", "country", "date_created",
                                        "quantity", "full_name", "order_status");
                        }});
                        setFieldSetMapper(new BeanWrapperFieldSetMapper<FileEntry>() {{
                                setTargetType(FileEntry.class);
                        }});
                }});
                return reader;
        }

        @Bean
        public JsonFileItemWriter<OutputEntry> writer() {
                return new JsonFileItemWriterBuilder<OutputEntry>()
                        .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                        .resource(new FileSystemResource( filePath + fileOutput))
                        .lineSeparator("\n")
                        .name("tradeJsonFileItemWriter")
                        .build();
        }

        @Bean
        public Job job() {
                LOGGER.info("create user job called");
                return jobBuilderFactory.get("job")
                        .incrementer(new RunIdIncrementer())
                        .flow(step())
                        .end()
                        .build();
        }

        @Bean
        public FileEntryProcessor processor() {
                LOGGER.info("create processor called");
                return new FileEntryProcessor();
        }

        @Bean
        public Step step() {
                LOGGER.info("create step called");
                return stepBuilderFactory.get("step")
                        .<FileEntry, OutputEntry>chunk(chunk)
                        .reader(reader())
                        .processor(processor())
                        .writer(writer())
                        .build();
        }
}

