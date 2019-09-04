package top.luhancc.mybatis.excutor.impl;

import top.luhancc.mybatis.core.DataSourceConfiguration;
import top.luhancc.mybatis.excutor.SQLExecutor;
import top.luhancc.mybatis.handler.AbstractResultMappingHandler;
import top.luhancc.mybatis.handler.impl.SingleResultMappingHandler;

import java.sql.*;

/**
 * 〈默认的SQL 执行器〉<br>
 *
 * @author luHan
 * @create 2019-08-30 14:46
 * @since 1.0.0
 */
public class DefaultSQLExecutor implements SQLExecutor {
    private DataSourceConfiguration xmlConfiguration;

    public DefaultSQLExecutor(DataSourceConfiguration xmlConfiguration) {
        this.xmlConfiguration = xmlConfiguration;
    }

    @Override
    public <T> T executor(String sql, Object[] parameter,Object returnType,Object resultType) throws Exception {
        Connection connection = getConnection();
        try(PreparedStatement pre = connection.prepareStatement(sql)) {
            System.out.println("SQL:" + sql);
            if(null != parameter){// 设置参数
                for (int i = 0; i < parameter.length; i++) {
                    System.out.println("参数:" + parameter[i] + "(" + parameter[i].getClass().getName() + ")");
                    pre.setString((i + 1), parameter[i].toString());
                }
            }
            try(ResultSet set = pre.executeQuery()){// 解析结果
                AbstractResultMappingHandler resultMappingHandler = AbstractResultMappingHandler.get(((Class) returnType).getSimpleName());
                if(null != resultMappingHandler){
                    returnType = resultMappingHandler.parse(set,resultType);
                }else{
                    returnType = SingleResultMappingHandler.SINGLE.parse(set, resultType);
                }
                return (T) returnType;
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    private Connection getConnection() throws Exception {
        return this.xmlConfiguration.getConnection();
    }
}
