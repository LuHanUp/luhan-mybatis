package top.luhancc.mybatis.handler.impl;

import top.luhancc.mybatis.handler.AbstractResultMappingHandler;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈集合类型的数据处理器〉<br>
 *
 * @author luHan
 * @create 2019-09-02 17:41
 * @since 1.0.0
 */
public class CollectionResultMappingHandler {
    public static AbstractResultMappingHandler<List> LIST = new AbstractResultMappingHandler<List>() {
        @Override
        public List parse(ResultSet resultSet, Object resultType) throws SQLException {
            ResultSetMetaData metaData = resultSet.getMetaData();
            List list = new ArrayList();
            // 遍历结果集
            while (resultSet.next()) {
                try {
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        String columnName = metaData.getColumnName(i);
                        Object value = resultSet.getObject(columnName);
                        Class<?> returnTypeClass = resultType.getClass();
                        Field field = returnTypeClass.getDeclaredField(columnName);
                        field.setAccessible(true);
                        field.set(resultType, value);
                    }
                    list.add(resultType);
                } catch (Exception e) {

                }
            }
            return list;
        }
    };
}
