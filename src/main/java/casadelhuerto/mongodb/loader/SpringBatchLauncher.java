package casadelhuerto.mongodb.loader;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SpringBatchLauncher {
    public static void main(String[] args) throws IOException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, JobRestartException, JobExecutionAlreadyRunningException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:*-config.xml");
        System.out.print("FOO!");
        JobLauncher launcher = context.getBean("jobLauncher", JobLauncher.class);
        Job job = context.getBean("loadRelationDatabaseRowsIntoMongoDbJob", Job.class);

        JobParametersBuilder paramBuilder = new JobParametersBuilder();
        /*
         * 
         */
        paramBuilder.addString("processing.date", "2012-01-16");
        JobExecution execution = launcher.run(job, paramBuilder.toJobParameters());
    }
}
