package casadelhuerto.mongodb.loader;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Required;

import java.util.Map;

/**
 * Maps a Java Map object to a MongoDB specific representation
 */
public class JavaMapToDBObjectProcessor implements ItemProcessor<Map<String, Object>, DBObject>{

    private Log logger = LogFactory.getLog(getClass());

    @Override
    public DBObject process(Map<String, Object> map) throws Exception {
        DBObject obj = new BasicDBObject();
        for (String key: map.keySet()) {
            obj.put(key, map.get(key));
        }
        return obj;
    }
}
