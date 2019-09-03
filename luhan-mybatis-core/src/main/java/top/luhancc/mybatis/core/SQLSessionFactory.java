package top.luhancc.mybatis.core;

import top.luhancc.mybatis.excutor.SQLExecutor;
import top.luhancc.mybatis.excutor.impl.DefaultSQLExecutor;

/**
 * 〈SQLSession的工厂类  负责产生SQLSession〉<br>
 *
 * @author luHan
 * @create 2019-09-03 13:46
 * @since 1.0.0
 */
public class SQLSessionFactory {
    private SQLExecutor executor;
    private DataSourceConfiguration dataSourceConfiguration = new DataSourceConfiguration();

    public SQLSessionFactory(String configurationPath) throws Exception {
        dataSourceConfiguration.build(configurationPath);
        this.executor = new DefaultSQLExecutor(dataSourceConfiguration);
    }

    public SQLSession opSession(){
        return new DefaultSQLSession(dataSourceConfiguration,executor);
    }
}
