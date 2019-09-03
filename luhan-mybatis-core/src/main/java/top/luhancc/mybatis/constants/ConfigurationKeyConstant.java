package top.luhancc.mybatis.constants;

import top.luhancc.mybatis.handler.AbstractResultMappingHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈configuration.xml中所有属性名称name的常量类>
 *
 * @author luHan
 * @create 2019-08-31 14:55
 * @since 1.0.0
 */
public class ConfigurationKeyConstant {
    /**
     * configuration.xml配置文件根节点的名称
     */
    public static final String ROOT_NAME = "configuration";

    /**
     * 数据库驱动类地址
     */
    public static final String DRIVER_CLASS_NAME = "driverClassName";

    /**
     * xml节点名称
     */
    public static final String PROPERTY = "property";

    /**
     * xml节点name属性
     */
    public static final String NAME = "name";

    /**
     * 数据库连接URL
     */
    public static final String URL = "url";

    /**
     *数据库用户名称
     */
    public static final String USERNAME = "username";

    /**
     * 数据库用户密码
     */
    public static final String PASSWORD = "password";

    /**
     * Mapper.xml存放地址
     */
    public static final String MAPPER_RESOURCE = "mapperResource";

    /**
     * Mapper.xml里namespace
     */
    public static final String NAMESPACE = "namespace";

    public static List<AbstractResultMappingHandler> CLASS_MAPPING_ENUMS = new ArrayList<>();
}
