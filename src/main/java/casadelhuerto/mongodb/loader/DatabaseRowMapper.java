package casadelhuerto.mongodb.loader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.FieldSetFactory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Reads data from a relational database table and converts it to a Map for later post-processing.
 */
public class DatabaseRowMapper implements RowMapper<Map<String, Object>> {

    private Log logger = LogFactory.getLog(getClass());

    /*
     * Uses metadata information to create the map entries.
     */
    @Override
    public Map<String, Object> mapRow(ResultSet resultSet, int i) throws SQLException {

        /*
         * Extract the column values into a Java map
         */
        Map<String, Object> resultMap = new HashMap<String, Object>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int c = 1; c <= columnCount; c++) {
            String columnName = metaData.getColumnName(c).toLowerCase();
            Object value = resultSet.getObject(c);
            logger.debug("Extracted value '" + value + "' from column '" + columnName + "'");
            resultMap.put(columnName, value);
        }

        return resultMap;
    }
}
