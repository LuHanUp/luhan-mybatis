package top.luhancc.mybatis.excutor.impl;

import top.luhancc.mybatis.configbean.MapperBean;
import top.luhancc.mybatis.core.DataSourceConfiguration;
import top.luhancc.mybatis.excutor.SQLExcutor;
import top.luhancc.mybatis.handler.AbstractResultMappingHandler;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * 〈默认的SQL 执行器〉<br>
 *
 * @author luHan
 * @create 2019-08-30 14:46
 * @since 1.0.0
 */
public class DefaultSQLExcutor implements SQLExcutor {
    private DataSourceConfiguration xmlConfiguration = new DataSourceConfiguration();

    @Override
    public <T> T excutor(String sql, Object parameter,Object returnType) {
        Connection connection = getConnection();
        try(PreparedStatement pre = connection.prepareStatement(sql)) {
            System.out.println("SQL:" + sql);
            System.out.println("参数:" + parameter + "(" + parameter.getClass().getName() + ")");
            //设置参数
            pre.setString(1, parameter.toString());
            try(ResultSet set = pre.executeQuery()){
                AbstractResultMappingHandler resultMappingHandler = AbstractResultMappingHandler.get(returnType.getClass().getSimpleName());
                if(null != resultMappingHandler){
                    returnType = resultMappingHandler.parse(set);
                }else{
                    // 遍历结果集
                    while (set.next()) {
                        ResultSetMetaData metaData = set.getMetaData();
                        for (int i = 1; i <= metaData.getColumnCount(); i++) {
                            String columnName = metaData.getColumnName(i);
                            Object value = set.getObject(columnName);
                            Class<?> returnTypeClass = returnType.getClass();
                            try {
                                Field field = returnTypeClass.getDeclaredField(columnName);
                                field.setAccessible(true);
                                field.set(returnType, value);
                            } catch (NoSuchFieldException e) {
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                return (T) returnType;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Connection getConnection() {
        try {
            return xmlConfiguration.build("database.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
