package top.luhancc.mybatis.handler.impl;

import top.luhancc.mybatis.handler.AbstractResultMappingHandler;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈返回Map类型的SQL结果数据处理器〉<br>
 *
 * @author luHan
 * @create 2019-09-04 13:37
 * @since 1.0.0
 */
public class MapResultMappingHandler {

    public static final AbstractResultMappingHandler<List<Map>> LIST_MAP = new AbstractResultMappingHandler<List<Map>>() {
        @Override
        @SuppressWarnings("unchecked")
        public List<Map> parse(ResultSet resultSet, Object resultType) throws SQLException {
            List<Map> result = new ArrayList<>();
            ResultSetMetaData metaData = resultSet.getMetaData();
            // 遍历结果集
            while (resultSet.next()) {
                Map map = new HashMap();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = resultSet.getObject(columnName);
                    map.put(columnName, value);
                }
                result.add(map);
            }
            return result;
        }
    };

    public static final AbstractResultMappingHandler<Map> MAP = new AbstractResultMappingHandler<Map>() {
        @Override
        @SuppressWarnings("unchecked")
        public Map parse(ResultSet resultSet, Object resultType) throws SQLException {
            ResultSetMetaData metaData = resultSet.getMetaData();
            Map map = new HashMap();
            // 遍历结果集
            while (resultSet.next()) {
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = resultSet.getObject(columnName);
                    map.put(columnName, value);
                }
                break;
            }
            return map;
        }
    };
}
