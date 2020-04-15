package top.luhancc.mybatis.example.handler;

import top.luhancc.mybatis.handler.AbstractResultMappingHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 〈自定义的数据转换器〉<br>
 *
 * @author luHan
 * @create 2020-04-15 15:58
 * @since 1.0.0
 */
public class MyMappingHandler extends AbstractResultMappingHandler<Object> {
    @Override
    public Object parse(ResultSet resultSet, Object resultType) throws SQLException {
        return new Object();
    }
}
