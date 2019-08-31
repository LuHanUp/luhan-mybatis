package top.luhancc.mybatis.configbean;

import lombok.Data;

/**
 * 〈database.xml 对应的Java Bean〉<br>
 *
 * @author luHan
 * @create 2019-08-31 15:01
 * @since 1.0.0
 */
@Data
public class DataBaseBean {
    /**
     * 数据库驱动类全名
     */
    private String driverClassName;

    /**
     * 数据库连接URL
     */
    private String url;

    /**
     * 数据库用户名称
     */
    private String username;

    /**
     * 数据库用户密码
     */
    private String password;

    /**
     * 存放Mapper.xml的路径
     */
    private String mapperResource;
}
