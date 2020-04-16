package top.luhancc.mybatis.handler.impl;

import top.luhancc.mybatis.handler.AbstractResultMappingHandler;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 〈集合类型的数据处理器〉<br>
 *
 * @author luHan
 * @create 2019-09-02 17:41
 * @since 1.0.0
 */
public class CollectionResultMappingHandler {
    public static AbstractResultMappingHandler<List<?>> LIST = new AbstractResultMappingHandler<List<?>>() {
        @Override
        public List<?> parse(ResultSet resultSet, Object resultType) throws SQLException{
            if(resultType instanceof Map){
                return MapResultMappingHandler.LIST_MAP.parse(resultSet,resultType);
            }
            ResultSetMetaData metaData = resultSet.getMetaData();
            List<Object> list = new ArrayList<>();
            // 遍历结果集
            while (resultSet.next()) {
                try {
                    Object newInstance = resultType.getClass().newInstance();
                    this.parseObject(metaData, resultSet, newInstance);
                    list.add(newInstance);
                } catch (Exception e) {
                    throw new SQLException("转换数据异常");
                }
            }
            return list;
        }
    };
}
