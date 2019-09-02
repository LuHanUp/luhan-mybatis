package top.luhancc.mybatis.handler;

import top.luhancc.mybatis.constants.DataBaseKeyConstant;
import top.luhancc.mybatis.handler.impl.BasicTypesResultMappingHandler;
import top.luhancc.mybatis.utils.ClassUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  抽象的结果映射处理器 E为SQL结果数据对象 <br>
 *      如果你需要将Mapper.xml中resultType返回成你想要的数据,你可以继承这个类来进行处理 <br>
 *          本框架默认的一些处理器是：①基本类型的处理器②对象集合处理器③Map处理器④单一对象处理器 <br>
 *  如果找不到存在的处理器,默认返回的是BasicTypesResultMappingHandler.STRING
 * @author luHan
 * @create 2019-09-02 11:01
 * @since 1.0.0
 */
public abstract class AbstractResultMappingHandler<E>{
    public abstract E parse(ResultSet resultSet,Object resultType) throws SQLException;

    public AbstractResultMappingHandler() {
        DataBaseKeyConstant.CLASS_MAPPING_ENUMS.add(this);
    }

    /**
     * 通过名称获取对应的处理器
     * @param name 可以是基本类型,可以是对象,可以是map,可以是List等等
     * @return
     */
    public static AbstractResultMappingHandler get(String name){
        for (AbstractResultMappingHandler abstractResultMappingHandler : DataBaseKeyConstant.CLASS_MAPPING_ENUMS) {
            if("int".equalsIgnoreCase(name)){
                return BasicTypesResultMappingHandler.INTEGER;
            }
            Class clazz = ClassUtil.getRealType(abstractResultMappingHandler.getClass());
            String simpleName = clazz.getSimpleName();
            if(simpleName.equalsIgnoreCase(name)){
                return abstractResultMappingHandler;
            }
        }
        return null;
    }

    public static void main(String[] args) throws SQLException {
        AbstractResultMappingHandler qweqwe = AbstractResultMappingHandler.get("float");
        System.out.println(qweqwe.parse(null,""));
    }
}
