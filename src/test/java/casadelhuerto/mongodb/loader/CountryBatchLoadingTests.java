package casadelhuerto.mongodb.loader;

import static org.junit.Assert.assertEquals;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:system-test-config.xml")
public class CountryBatchLoadingTests {

   	@Autowired
    JobLauncherTestUtils testUtils;

    MongoOperations mongoOperations;
    JdbcOperations originDatabaseOperations;

   	@Autowired
    @Required
   	public void initJdbcOperations(@Qualifier("originDataSource") DataSource dataSource) {
   		this.originDatabaseOperations = new JdbcTemplate(dataSource);
   	}

    @Autowired
    @Required
   	public void initMongoOperations(MongoTemplate mongoTemplate) {
   		this.mongoOperations = mongoTemplate;
   	}

   	@Test
   	public void runBatchJob() throws Exception {
   		JobExecution execution = testUtils.launchJob();

   		assertEquals(ExitStatus.COMPLETED, execution.getExitStatus());

   		long originalCountries = originDatabaseOperations.queryForLong("select count(*) from Country") ;
        long loadedCountries = mongoOperations.count(new Query(), "countries");
   		assertEquals(originalCountries, loadedCountries);

        /*
         * Since MongoDB does not support "traditional" transactions across documents with rollback
         * we delete the collection here.
         *
         * Uncomment the following line to be able to see the created collection from the MongoDB console.
         */
         mongoOperations.dropCollection("countries");
   	}

}
