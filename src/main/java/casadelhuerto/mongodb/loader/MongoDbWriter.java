package casadelhuerto.mongodb.loader;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.mongodb.core.MongoOperations;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * Stores an item in a MongoDB database
 */
public class MongoDbWriter implements ItemWriter<DBObject>{

    private Log logger = LogFactory.getLog(getClass());
    private String collectionName;
    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void write(List<? extends DBObject> items) throws Exception {
        logger.debug("Writing " + items.size() + " items to collection '" + collectionName + "' in MongoDB");
        // FIXME: there has to be a better way of batch saving!
        for (DBObject item: items) {
            mongoOperations.insert(item, collectionName);
        }
    }

    @Required
    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    @Required
    public void setMongoOperations(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }
}
