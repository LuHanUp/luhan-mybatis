package top.luhancc.mybatis.excutor;

/**
 * 〈SQL 执行器〉<br>
 *
 * @author luHan
 * @create 2019-08-30 14:44
 * @since 1.0.0
 */
public interface SQLExecutor {
    <T> T executor(String sql, Object[] parameter,Object returnType,Object resultType) throws Exception;
}
