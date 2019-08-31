package top.luhancc.mybatis.core;

import org.dom4j.DocumentException;
import top.luhancc.mybatis.configbean.MapperBean;
import top.luhancc.mybatis.excutor.SQLExcutor;
import top.luhancc.mybatis.excutor.impl.DefaultSQLExcutor;

import java.lang.reflect.Proxy;
import java.sql.SQLException;

/**
 * 〈SQL Session类〉<br>
 *
 * @author luHan
 * @create 2019-08-30 14:43
 * @since 1.0.0
 */
public class SQLSession {
    private SQLExcutor excutor = new DefaultSQLExcutor();

    private DataSourceConfiguration dataSourceConfiguration = new DataSourceConfiguration();

    public SQLSession() {
        try {
            dataSourceConfiguration.build("database.xml");
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public <T> T excutor(String statement, Object parameter,Object returnType){
        return excutor.excutor(statement, parameter,returnType);
    }

    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<T> clas){
        //动态代理调用
        return (T)Proxy.newProxyInstance(clas.getClassLoader(),new Class[]{clas},
                new MapperProxy(dataSourceConfiguration,this));
    }
}
