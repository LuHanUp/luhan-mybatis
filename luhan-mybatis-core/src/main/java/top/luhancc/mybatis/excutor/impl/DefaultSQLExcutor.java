package top.luhancc.mybatis.excutor.impl;

import top.luhancc.mybatis.configbean.MapperBean;
import top.luhancc.mybatis.core.DataSourceConfiguration;
import top.luhancc.mybatis.excutor.SQLExcutor;
import top.luhancc.mybatis.handler.AbstractResultMappingHandler;
import top.luhancc.mybatis.handler.impl.SingleResultMappingHandler;

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
    public <T> T excutor(String sql, Object[] parameter,Object returnType,Object resultType) {
        Connection connection = getConnection();
        try(PreparedStatement pre = connection.prepareStatement(sql)) {
            System.out.println("SQL:" + sql);
            if(null != parameter){
                for (int i = 0; i < parameter.length; i++) {
                    System.out.println("参数:" + parameter[i] + "(" + parameter[i].getClass().getName() + ")");
                    pre.setString((i + 1), parameter[i].toString());
                }
            }
            //设置参数
            try(ResultSet set = pre.executeQuery()){
                AbstractResultMappingHandler resultMappingHandler = AbstractResultMappingHandler.get(((Class) returnType).getSimpleName());
                if(null != resultMappingHandler){
                    returnType = resultMappingHandler.parse(set,resultType);
                }else{
                    returnType = SingleResultMappingHandler.SINGLE.parse(set, resultType);
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
