package top.luhancc.mybatis.handler.impl;

import top.luhancc.mybatis.handler.AbstractResultMappingHandler;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

/**
 * 〈返回Map类型的SQL结果数据处理器〉<br>
 *
 * @author luHan
 * @create 2019-09-04 13:37
 * @since 1.0.0
 */
public class MapResultMappingHandler {

    /**
     * List<Map<String,Object>>类型的数据转换器
     */
    public static final AbstractResultMappingHandler<List<Map<String,Object>>> LIST_MAP = new AbstractResultMappingHandler<List<Map<String,Object>>>() {
        @Override
        @SuppressWarnings("unchecked")
        public List<Map<String,Object>> parse(ResultSet resultSet, Object resultType) throws SQLException {
            List<Map<String,Object>> result = new ArrayList<>();
            ResultSetMetaData metaData = resultSet.getMetaData();
            // 遍历结果集
            while (resultSet.next()) {
                Map<String,Object> map = lineToMap(resultSet, metaData);
                result.add(map);
            }
            return result;
        }
    };

    /**
     * Map<String,Object>类型的数据转换器
     */
    public static final AbstractResultMappingHandler<Map<String,Object>> MAP = new AbstractResultMappingHandler<Map<String,Object>>() {
        @Override
        @SuppressWarnings("unchecked")
        public Map<String,Object> parse(ResultSet resultSet, Object resultType) throws SQLException {
            ResultSetMetaData metaData = resultSet.getMetaData();
            Map<String,Object> map = Collections.EMPTY_MAP;
            // 遍历结果集
            while (resultSet.next()) {
                map = lineToMap(resultSet, metaData);
                break;
            }
            return map;
        }
    };

    /**
     * 将数据库中的列名设置为Map的Key，列的数据设置为Map的Value
     * @param resultSet
     * @param metaData
     * @throws SQLException
     */
    private static Map<String,Object> lineToMap(ResultSet resultSet, ResultSetMetaData metaData) throws SQLException {
        int fieldCount = metaData.getColumnCount();
        Map<String,Object> map = new HashMap<>(fieldCount);
        for (int i = 1; i <= fieldCount; i++) {
            String columnName = metaData.getColumnName(i);
            Object value = resultSet.getObject(columnName);
            map.put(columnName, value);
        }
        return map;
    }
}
