package top.luhancc.mybatis.handler.impl;

import top.luhancc.mybatis.handler.AbstractResultMappingHandler;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈单对象的处理器〉<br>
 *
 * @author luHan
 * @create 2019-09-02 18:23
 * @since 1.0.0
 */
public class SingleResultMappingHandler {
    public static AbstractResultMappingHandler<Object> SINGLE = new AbstractResultMappingHandler<Object>() {
        @Override
        public Object parse(ResultSet resultSet, Object resultType) throws SQLException {
            ResultSetMetaData metaData = resultSet.getMetaData();
            // 遍历结果集
            while (resultSet.next()) {
                try {
                    this.parseObject(metaData, resultSet, resultType);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return resultType;
        }
    };
}
