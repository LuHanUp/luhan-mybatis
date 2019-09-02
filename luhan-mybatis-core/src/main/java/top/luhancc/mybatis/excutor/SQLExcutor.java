package top.luhancc.mybatis.excutor;

/**
 * 〈SQL 执行器〉<br>
 *
 * @author luHan
 * @create 2019-08-30 14:44
 * @since 1.0.0
 */
public interface SQLExcutor {
    <T> T excutor(String sql, Object[] parameter,Object returnType,Object resultType);
}
