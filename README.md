# 纯手写Mybatis框架的实现(仅供学习使用)
## 目前实现功能:  
* Mapper.java 接口的定义已经其对应的Mapper.xml的定义
* Mapper.xml中书写SQL(参数暂不支持#{}和${}模式)  
**支持returnType直接填写类的全路径** 
* 支持将返回结果直接映射到returnType中(需要属性名称和列名一致)  
* 支持Mapper接口返回List<E>数据格式  
* 支持Mapper接口返回Map数据格式  
* 支持Mapper接口返回List<Map>数据格式  
<!-- * 支持用户自定义数据处理器,如果需要自定义返回数据对象只需要extends AbstractResultMappingHandler对象重写parse(ResultSet resultSet)方法即可 -->
#### 模块介绍
luhan-mybatis 父工程  
luhan-mybatis-core 核心工程(代码都在此工程里)  
luhan-mybatis-example 演示DEMO工程  

## 目录说明
```
│  pom.xml  父工程的POM文件
│  README.md    整个项目的说明文件
│      
├─luhan-mybatis-core    手写mybatis核心模块
│  │  pom.xml   luhan-mybatis-core的POM文件
│  ├─src
│  │  ├─main
│  │  │  ├─java
│  │  │  │  └─top
│  │  │  │      └─luhancc
│  │  │  │          └─mybatis
│  │  │  │              ├─configbean    存放各种数据结构的bean
│  │  │  │              │      DataBaseBean.java    数据源信息
│  │  │  │              │      FunctionBean.java    mapper接口的所有方法信息
│  │  │  │              │      MapperBean.java  mapper接口的相关信息(包含FunctionBean)
│  │  │  │              │      
│  │  │  │              ├─constants 存放项目中用到的常量类
│  │  │  │              │      ConfigurationKeyConstant.java    configuration配置文件中的一些常量(包含节点名称,节点的属性等等)
│  │  │  │              │      
│  │  │  │              ├─core  核心包
│  │  │  │              │      DataSourceConfiguration.java 解析configuration配置文件,将其内容加载到内存中(其对象是唯一一个)
│  │  │  │              │      DefaultSQLSession.java   默认的SQLSession实现类
│  │  │  │              │      MapperProxy.java Mapper接口的代理实现类
│  │  │  │              │      SQLSession.java  SQLSession接口
│  │  │  │              │      SQLSessionFactory.java   创建SQLSession的工厂类
│  │  │  │              │      
│  │  │  │              ├─enums
│  │  │  │              │      ResultTypeMappingsEnum.java  存放基本类型对应的java包装类的类路径
│  │  │  │              │      
│  │  │  │              ├─excutor   执行SQL语句处理相关包
│  │  │  │              │  │  SQLExecutor.java  执行SQL语句处理接口
│  │  │  │              │  │  
│  │  │  │              │  └─impl   存放SQLExecutor的所有实现类
│  │  │  │              │          DefaultSQLExecutor.java  默认的执行SQL语句处理接口实现类
│  │  │  │              │          
│  │  │  │              ├─handler   数据(SQL查询的数据)解析处理相关包
│  │  │  │              │  │  AbstractResultMappingHandler.java 抽象的数据解析类
│  │  │  │              │  │  
│  │  │  │              │  └─impl   存放AbstractResultMappingHandler实现类
│  │  │  │              │          BasicTypesResultMappingHandler.java  处理返回基本类型的数据处理类
│  │  │  │              │          CollectionResultMappingHandler.java  处理返回集合类型的数据处理类
│  │  │  │              │          MapResultMappingHandler.java 处理返回Map类型的数据处理类
│  │  │  │              │          SingleResultMappingHandler.java  处理返回单对象的数据处理类
│  │  │  │              │          
│  │  │  │              └─utils 存放需要用到的相关工具类
│  │  │  │                      ClassUtil.java  关于class的相关操作
│     │                      
└─luhan-mybatis-example 测试使用luhan-mybatis-core的模块
    │  pom.xml  luhan-mybatis-example的POM文件
    │  
    ├─src
    │  ├─main
    │  │  ├─java
    │  │  │  └─top
    │  │  │      └─luhancc
    │  │  │          └─mybatis
    │  │  │              └─example
    │  │  │                  │  LuHanMybatisApplication.java    主启动类
    │  │  │                  │  
    │  │  │                  ├─entity   存放各种实体
    │  │  │                  │      Users.java  对应Users表
    │  │  │                  │      
    │  │  │                  └─mapper   存放Mapper接口
    │  │  │                          UserMapper.java    Users实体操作的Mapper接口
    │  │  │                          
    │  │  └─resources
    │  │      │  configuration.xml  mybatis的配置文件
    │  │      │  
    │  │      └─mapper
    │  │              UsersMapper.xml   UserMapper接口的mapper定义文件
```

## 代码使用示例
#### 引入luhan-mybatis-core
    <dependency>
        <groupId>top.luhancc</groupId>
        <artifactId>luhan-mybatis-core</artifactId>
        <version>1.0-SNAPSHOT</version>
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
        Map<String, Object> selectMapByName(String name);
        List<Map<String, Object>> selectMapsAll();
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
        <select id="selectMapByName" resultType ="map">
            select * from user where username = ?
        </select>
        <select id="selectMapsAll" resultType ="map">
            select * from user
        </select>
    </mapper>
#### 获取Mapper.java接口
    SQLSessionFactory sqlSessionFactory = new SQLSessionFactory("configuration.xml");
    SQLSession sqlsession = sqlSessionFactory.opSession();
    UserMapper mapper = sqlsession.getMapper(UserMapper.class);
#### 调用Mapper.java相关方法
    public static void main(String[] args) throws Exception {
        SQLSessionFactory sqlSessionFactory = new SQLSessionFactory("configuration.xml");
        SQLSession sqlsession = sqlSessionFactory.opSession();

        UserMapper mapper = sqlsession.getMapper(UserMapper.class);

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

        Map<String, Object> map = mapper.selectMapByName("luhan");
        System.out.println("mapper.selectMapByName(\"luhan\"):"+map);
        System.out.println();

        List<Map<String, Object>> mapList = mapper.selectMapsAll();
        System.out.println("mapper.selectMapsAll():"+mapList);
        System.out.println();
    }
#### 结果示例
![luhan-mybatis-example运行结果](http://i2.tiimg.com/698680/5b1e127b9bb401f3.png)
#### 当前只是简单的实现,后面会逐渐完善 
