package integration.service;

import integration.registry.ConnectionRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;

@Service
public class ParserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParserService.class);

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job fileProcessorJob;

    @Autowired
    ConnectionRegistry connectionRegistry;

    @PostConstruct
    private void init() {
        LOGGER.info("Parser service initialized");
        connectionRegistry.init();
    }

    @PreDestroy
    public void destroy() {
        LOGGER.info("Parser service shutdown");
        connectionRegistry.shutdown();
    }

    private JobParameters createInitialJobParameterMap() {
        Map<String, JobParameter> m = new HashMap<>();
        m.put("time", new JobParameter(System.currentTimeMillis()));
        return  new JobParameters(m);
    }

    void parse() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        LOGGER.info("file parsing started");
        jobLauncher.run(fileProcessorJob, createInitialJobParameterMap());
    }
}
