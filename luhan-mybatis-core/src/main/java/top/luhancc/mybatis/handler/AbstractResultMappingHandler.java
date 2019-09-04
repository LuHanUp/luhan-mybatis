package top.luhancc.mybatis.handler;

import top.luhancc.mybatis.constants.ConfigurationKeyConstant;
import top.luhancc.mybatis.handler.impl.BasicTypesResultMappingHandler;
import top.luhancc.mybatis.utils.ClassUtil;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 *  抽象的结果映射处理器 E为SQL结果数据对象 <br>
 *      如果你需要将Mapper.xml中resultType返回成你想要的数据,你可以继承这个类来进行处理 <br>
 *          本框架默认的一些处理器是：①基本类型的处理器②对象集合处理器③Map处理器④单一对象处理器 <br>
 *  如果找不到存在的处理器抛出异常
 * @author luHan
 * @create 2019-09-02 11:01
 * @since 1.0.0
 */
public abstract class AbstractResultMappingHandler<E>{
    public abstract E parse(ResultSet resultSet,Object resultType) throws SQLException;

    protected AbstractResultMappingHandler() {
        ConfigurationKeyConstant.CLASS_MAPPING_ENUMS.add(this);
    }

    /**
     * 通过名称获取对应的处理器
     * @param name 可以是基本类型,可以是对象,可以是map,可以是List等等
     * @return
     */
    public static AbstractResultMappingHandler get(String name){
        for (AbstractResultMappingHandler abstractResultMappingHandler : ConfigurationKeyConstant.CLASS_MAPPING_ENUMS) {
            if("int".equalsIgnoreCase(name)){
                return BasicTypesResultMappingHandler.INTEGER;
            }
            Class clazz = ClassUtil.getRealType(abstractResultMappingHandler.getClass());
            String simpleName = clazz.getSimpleName();
            if(simpleName.equalsIgnoreCase(name)){
                return abstractResultMappingHandler;
            }
        }
        throw new RuntimeException(String.format("ConfigurationKeyConstant.CLASS_MAPPING_ENUMS中没有对应[%s]的数据处理类",name));
    }

    /**
     * 基础的将RestSet对象给指定的resultType进行赋值
     * @param metaData ResultSetMetaData
     * @param resultSet ResultSet
     * @param resultType resultType
     */
    protected void parseObject(ResultSetMetaData metaData,ResultSet resultSet, Object resultType) throws SQLException, IllegalAccessException, NoSuchFieldException {
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String columnName = metaData.getColumnName(i);
            Object value = resultSet.getObject(columnName);
            Class<?> returnTypeClass = resultType.getClass();
            Field field = returnTypeClass.getDeclaredField(columnName);
            field.setAccessible(true);
            field.set(resultType, value);
        }
    }
}
