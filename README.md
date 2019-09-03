# 纯手写Mybatis框架的实现(仅供学习使用)
## 目前实现功能:  
* Mapper.java 接口的定义已经其对应的Mapper.xml的定义
* Mapper.xml中书写SQL(参数暂不支持#{}和${}模式)  
**支持returnType直接填写类的全路径** 
* 支持将返回结果直接映射到returnType中(需要属性名称和列名一致)  
* 支持Mapper接口返回List<E>数据格式  
<!-- * 支持用户自定义数据处理器,如果需要自定义返回数据对象只需要extends AbstractResultMappingHandler对象重写parse(ResultSet resultSet)方法即可 -->
#### 模块介绍
luhan-mybatis 父工程  
luhan-mybatis-core 核心工程(代码都在此工程里)  
luhan-mybatis-example 演示DEMO工程  

## 代码使用示例
#### 引入luhan-mybatis-core
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.16.22</version>
    </dependency>
#### configuration.xml mybatis配置文件
    <?xml version="1.0" encoding="UTF-8"?>
    <configuration>
        <property name="driverClassName">com.mysql.jdbc.Driver</property>
        <property name="url">jdbc:mysql://localhost:3306/test?useUnicode=true&amp;characterEncoding=utf8</property>
        <property name="username">root</property>
        <property name="password">root</property>
        <property name="mapperResource">mapper/UsersMapper.xml</property>
    </configuration>
#### UserMapper接口类
    public interface UserMapper {
        Users selectById(String id);
        Users selectByName(String name);
        String selectNameById(String id);
        int selectIdByName(String name);
        List<Users> selectAll();
    }
#### UserMapper.xml(路径为mapper/UsersMapper.xml)
    <?xml version="1.0" encoding="UTF-8"?>
    <mapper namespace="top.luhancc.mybatis.example.mapper.UserMapper">
        <select id="selectById" resultType ="top.luhancc.mybatis.example.entity.Users">
            select * from user where id = ?
        </select>
        <select id="selectByName" resultType ="top.luhancc.mybatis.example.entity.Users">
            select * from user where username = ?
        </select>
        <select id="selectNameById" resultType ="string">
            select username from user where id = ?
        </select>
        <select id="selectIdByName" resultType ="int">
            select id from user where username = ?
        </select>
        <select id="selectAll" resultType ="top.luhancc.mybatis.example.entity.Users">
            select * from user
        </select>
    </mapper>
#### 获取Mapper.java接口
    SQLSessionFactory sqlSessionFactory = new SQLSessionFactory("configuration.xml");
    SQLSession sqlsession = sqlSessionFactory.opSession();
    UserMapper mapper = sqlsession.getMapper(UserMapper.class);
#### 调用Mapper.java相关方法
        Users user = mapper.selectById("1");
        System.out.println("mapper.selectById(\"1\"):"+user);
        System.out.println();

        Users user2 = mapper.selectByName("luhan");
        System.out.println("mapper.selectByName(\"luhan\"):"+user2);
        System.out.println();

        String username = mapper.selectNameById("1");
        System.out.println("mapper.selectNameById(\"1\"):"+username);
        System.out.println();

        int id = mapper.selectIdByName("luhan");
        System.out.println("id:"+id);
        System.out.println();

        List<Users> usersList = mapper.selectAll();
        System.out.println("mapper.selectAll():"+usersList);
#### 结果示例
![luhan-mybatis-example运行结果](http://i2.tiimg.com/698680/d5e82b70fd07c744.png)
#### 当前只是简单的实现,后面会逐渐完善 
