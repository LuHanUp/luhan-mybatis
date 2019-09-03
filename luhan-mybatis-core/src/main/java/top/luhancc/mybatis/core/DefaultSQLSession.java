package top.luhancc.mybatis.core;

import top.luhancc.mybatis.excutor.SQLExcutor;

import java.lang.reflect.Proxy;

/**
 * 〈默认的SQLSession实现类〉<br>
 *
 * @author luHan
 * @create 2019-09-03 13:50
 * @since 1.0.0
 */
public class DefaultSQLSession implements SQLSession{

    private DataSourceConfiguration dataSourceConfiguration;
    private SQLExcutor sqlExcutor;

    public DefaultSQLSession(DataSourceConfiguration dataSourceConfiguration, SQLExcutor sqlExcutor) {
        this.dataSourceConfiguration = dataSourceConfiguration;
        this.sqlExcutor = sqlExcutor;
    }

    @Override
    @SuppressWarnings("all")
    public <T> T getMapper(Class<T> clas) {
        //动态代理调用
        return (T)Proxy.newProxyInstance(clas.getClassLoader(),new Class[]{clas},
                new MapperProxy(this.sqlExcutor,this,dataSourceConfiguration));
    }
}
