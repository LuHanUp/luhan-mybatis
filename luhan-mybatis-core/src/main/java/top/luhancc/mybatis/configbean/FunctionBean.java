package top.luhancc.mybatis.configbean;

import lombok.Data;

/**
 * 〈包含SQL类型，方法名称，SQL语句，返回类型，参数类型〉<br>
 *
 * @author luHan
 * @create 2019-08-30 13:55
 * @since 1.0.0
 */
@Data
public class FunctionBean {
    private String sqltype;// SQL类型
    private String funcName; // 方法名称
    private String sql; // SQL语句
    private Object resultType; // 返回类型
    private String parameterType; // 参数类型
}
