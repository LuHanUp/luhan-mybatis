package top.luhancc.mybatis.core;

import top.luhancc.mybatis.excutor.SQLExcutor;
import top.luhancc.mybatis.excutor.impl.DefaultSQLExcutor;

/**
 * 〈SQLSession的工厂类  负责产生SQLSession〉<br>
 *
 * @author luHan
 * @create 2019-09-03 13:46
 * @since 1.0.0
 */
public class SQLSessionFactory {
    private SQLExcutor excutor;
    private DataSourceConfiguration dataSourceConfiguration = new DataSourceConfiguration();

    public SQLSessionFactory(String configuractionPath) throws Exception {
        dataSourceConfiguration.build(configuractionPath);
        this.excutor = new DefaultSQLExcutor(dataSourceConfiguration);
    }

    public SQLSession opSession(){
        return new DefaultSQLSession(dataSourceConfiguration,excutor);
    }
}
