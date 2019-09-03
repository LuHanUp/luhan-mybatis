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

#### 当前只是简单的实现,后面会逐渐完善 
