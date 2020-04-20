package top.luhancc.mybatis.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 〈数据库转换为指定类型的基础接口〉<br>
 *
 * @author luHan
 * @create 2020/4/16 13:31
 * @since 1.0.0
 */
public interface ResultMappingHandler<E> {
    /**
     * 转换数据库中的数据为指定的类型E
     * @param resultSet
     * @param resultType
     * @return
     * @throws Exception
     */
    public abstract E parse(ResultSet resultSet, Object resultType) throws Exception;
}
