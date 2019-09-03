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
public interface SQLSession {
    <T> T getMapper(Class<T> clas);
    // <T> T select(Class<T> clas);
    // <T> T update(Class<T> clas);
    // <T> T insert(Class<T> clas);
    // <T> T delete(Class<T> clas);
}
