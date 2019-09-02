package top.luhancc.mybatis.handler.impl;

import top.luhancc.mybatis.handler.AbstractResultMappingHandler;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * 〈java中基本类型的处理器〉<br>
 *
 * @author luHan
 * @create 2019-09-02 11:46
 * @since 1.0.0
 */
public class BasicTypesResultMappingHandler {
    public static AbstractResultMappingHandler<String> STRING = new AbstractResultMappingHandler<String>() {
        @Override
        public String parse(ResultSet resultSet) throws SQLException {
            ResultSetMetaData metaData = resultSet.getMetaData();
            while(resultSet.next()){
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String columnName = metaData.getColumnName(i);
                    return resultSet.getString(columnName);
                }
            }
            return "";
        }
    };

    public static AbstractResultMappingHandler<Integer> INTEGER = new AbstractResultMappingHandler<Integer>() {
        @Override
        public Integer parse(ResultSet resultSet) throws SQLException {
            ResultSetMetaData metaData = resultSet.getMetaData();
            while(resultSet.next()){
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String columnName = metaData.getColumnName(i);
                    return resultSet.getInt(columnName);
                }
            }
            return null;
        }
    };

    public static AbstractResultMappingHandler<Double> DOUBLE = new AbstractResultMappingHandler<Double>() {
        @Override
        public Double parse(ResultSet resultSet) throws SQLException {
            ResultSetMetaData metaData = resultSet.getMetaData();
            while(resultSet.next()){
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String columnName = metaData.getColumnName(i);
                    return resultSet.getDouble(columnName);
                }
            }
            return null;
        }
    };

    public static AbstractResultMappingHandler<Float> FLOAT = new AbstractResultMappingHandler<Float>() {
        @Override
        public Float parse(ResultSet resultSet) throws SQLException {
            ResultSetMetaData metaData = resultSet.getMetaData();
            while(resultSet.next()){
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String columnName = metaData.getColumnName(i);
                    return resultSet.getFloat(columnName);
                }
            }
            return null;
        }
    };

    public static AbstractResultMappingHandler<Long> LONG = new AbstractResultMappingHandler<Long>() {
        @Override
        public Long parse(ResultSet resultSet) throws SQLException {
            ResultSetMetaData metaData = resultSet.getMetaData();
            while(resultSet.next()){
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String columnName = metaData.getColumnName(i);
                    return resultSet.getLong(columnName);
                }
            }
            return null;
        }
    };
}
