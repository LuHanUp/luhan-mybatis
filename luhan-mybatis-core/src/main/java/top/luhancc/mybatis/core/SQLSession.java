package top.luhancc.mybatis.core;

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
