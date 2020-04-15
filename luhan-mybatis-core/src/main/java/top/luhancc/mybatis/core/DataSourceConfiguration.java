package top.luhancc.mybatis.core;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import top.luhancc.mybatis.configbean.DataBaseBean;
import top.luhancc.mybatis.configbean.FunctionBean;
import top.luhancc.mybatis.configbean.MapperBean;
import top.luhancc.mybatis.constants.ConfigurationKeyConstant;
import top.luhancc.mybatis.enums.ResultTypeMappingsEnum;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈读取数据库配置信息,返回处理后的Environment〉<br>
 *
 * @author luHan
 * @create 2019-08-30 13:58
 * @since 1.0.0
 */
public class DataSourceConfiguration {
    private static ClassLoader classLoader = DataSourceConfiguration.class.getClassLoader();
    /**
     * 存储Mapper.java和Mapper.xml映射数据
     *  key:Mapper.java的全路径
     *  value:该Mapper.java对应的Mapper.xml名称
     */
    private static final Map<String,String> mapperClassXmlMap = new ConcurrentHashMap<>();

    /**
     * 数据库相关配置信息
     */
    private DataBaseBean dataBaseBean;

    /**
     * 通过configuration.xml内容
     * @param resource configuration.xml所在路径
     */
    public void build(String resource) throws Exception {
        evalDataSource(getRootElement(resource));
    }

    /**
     * 获取根节点
     * @param resource configuration.xml文件地址
     */
    private Element getRootElement(String resource) throws DocumentException {
        InputStream resourceStream = classLoader.getResourceAsStream(resource);
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(resourceStream);
        return document.getRootElement();
    }

    /**
     * 获取根节点
     * @param file configuration.xml文件
     */
    private Element getRootElement(File file) throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(file);
        return document.getRootElement();
    }

    /**
     * 解析configuration.xml内容
     * @param rootElement 根节点,必须为configuration
     */
    private void evalDataSource(Element rootElement) throws Exception {
        if(!ConfigurationKeyConstant.ROOT_NAME.equals(rootElement.getName())){
            throw new RuntimeException(String.format("configuration.xml 根节点必须是<%s>",ConfigurationKeyConstant.ROOT_NAME));
        }
        dataBaseBean = parseToDataBaseBean(rootElement);
        if(StringUtils.isEmpty(dataBaseBean.getDriverClassName())){
            throw new RuntimeException(String.format("configuration.xml 必须有<%s>属性",ConfigurationKeyConstant.DRIVER_CLASS_NAME));
        }
        if(StringUtils.isEmpty(dataBaseBean.getUrl())){
            throw new RuntimeException(String.format("configuration.xml 必须有<%s>属性",ConfigurationKeyConstant.URL));
        }
        if(StringUtils.isEmpty(dataBaseBean.getUsername())){
            throw new RuntimeException(String.format("configuration.xml 必须有<%s>属性",ConfigurationKeyConstant.USERNAME));
        }
        if(StringUtils.isEmpty(dataBaseBean.getPassword())){
            throw new RuntimeException(String.format("configuration.xml 必须有<%s>属性",ConfigurationKeyConstant.PASSWORD));
        }

        // 解析Mapper.xml内容
        parseMapperClassXmlMap(dataBaseBean.getMapperResource());

        // 添加默认的MappingHandler
        Class.forName("top.luhancc.mybatis.handler.impl.BasicTypesResultMappingHandler");
        Class.forName("top.luhancc.mybatis.handler.impl.CollectionResultMappingHandler");
        Class.forName("top.luhancc.mybatis.handler.impl.MapResultMappingHandler");
    }

    /**
     * 通过 configuration.xml 内容构建DataBaseBean对象
     * @param rootElement
     * @return
     */
    private DataBaseBean parseToDataBaseBean(Element rootElement) {
        DataBaseBean dataBaseBean = new DataBaseBean();

        for (Element item : (List<Element>)rootElement.elements(ConfigurationKeyConstant.PROPERTY)) {
            // 节点的名称,也就是数据库配置名称
            String elementName = item.attributeValue(ConfigurationKeyConstant.NAME);
            String value = getValue(item);
            switch (elementName){
                case ConfigurationKeyConstant.DRIVER_CLASS_NAME:
                    dataBaseBean.setDriverClassName(value);
                    break;
                case ConfigurationKeyConstant.MAPPER_RESOURCE:
                    dataBaseBean.setMapperResource(value);
                    break;
                case ConfigurationKeyConstant.USERNAME:
                    dataBaseBean.setUsername(value);
                    break;
                case ConfigurationKeyConstant.PASSWORD:
                    dataBaseBean.setPassword(value);
                    break;
                case ConfigurationKeyConstant.URL:
                    dataBaseBean.setUrl(value);
                    break;
            }
        }
        return dataBaseBean;
    }

    //获取property属性的值,如果有value值,则读取 没有设置value,则读取内容
    private  String getValue(Element node) {
        return node.hasContent() ? node.getText() : node.attributeValue("value");
    }

    /**
     * 解析Mapper.xml
     * @param mapperResource
     * @throws DocumentException
     */
    private void parseMapperClassXmlMap(String mapperResource) throws DocumentException, UnsupportedEncodingException {
        String path = classLoader.getResource(mapperResource).getPath();
        path = URLDecoder.decode(path, "UTF-8");
        File file = new File(path);
        if (file.isDirectory()){
            File[] files = file.listFiles();
            for (File file1 : files) {
                parseMapperClassXmlMap(file1);
            }
        }
        parseMapperClassXmlMap(file);
    }
    private void parseMapperClassXmlMap(File file) throws DocumentException {
        if(file.isDirectory()){
            // 递归处理
            File[] files = file.listFiles();
            for (File file1 : files) {
                parseMapperClassXmlMap(file1);
            }
        }else{
            Element rootElement = getRootElement(file);
            String namespace = rootElement.attributeValue(ConfigurationKeyConstant.NAMESPACE);
            mapperClassXmlMap.put(namespace, file.getPath());
        }
    }

    /**
     * 根据Mapper文件生成MapperBean
     * @param mapperClassPath Mapper.java路径
     * @return
     */
    public MapperBean readMapper(String mapperClassPath) throws Exception {
        MapperBean mapper = new MapperBean();
        String mapperXmlPath = mapperClassXmlMap.get(mapperClassPath);
        File mapperXmlFile = null;
        try {
            mapperXmlFile = new File(mapperXmlPath);
        } catch (Exception e) {
            throw new RuntimeException(String.format("不存在namespace=%s的Mapper.xml文件",mapperClassPath));
        }
        mapper.setMapperXmlName(mapperXmlFile.getAbsolutePath());
        Element root = getRootElement(mapperXmlFile);
        mapper.setInterfaceName(root.attributeValue("namespace").trim()); //把mapper节点的nameSpace值存为接口名
        List<FunctionBean> list = new ArrayList<>(); //用来存储方法的List
        for (Iterator rootIter = root.elementIterator(); rootIter.hasNext(); ) {//遍历根节点下所有子节点
            FunctionBean fun = new FunctionBean();    //用来存储一条方法的信息
            Element e = (Element) rootIter.next();
            String sqlType = e.getName().trim();
            String funcName = e.attributeValue("id").trim();
            String sql = e.getText().trim();
            String resultType = e.attributeValue("resultType").trim();
            fun.setSqltype(sqlType);
            fun.setFuncName(funcName);
            Object newInstance = null;
            try {
                ResultTypeMappingsEnum resultTypeMappingsEnum = ResultTypeMappingsEnum.get(resultType);
                if(null != resultTypeMappingsEnum){
                    if("java.lang.Integer".equalsIgnoreCase(resultTypeMappingsEnum.getClassName())){
                        newInstance = 0;
                    }else if("java.lang.Float".equalsIgnoreCase(resultTypeMappingsEnum.getClassName())){
                        newInstance = 0F;
                    }else if("java.lang.Double".equalsIgnoreCase(resultTypeMappingsEnum.getClassName())){
                        newInstance = 0D;
                    }else if("java.lang.Long".equalsIgnoreCase(resultTypeMappingsEnum.getClassName())){
                        newInstance = 0L;
                    }else{
                        newInstance = Class.forName(resultTypeMappingsEnum.getClassName()).newInstance();
                    }
                }else{
                    newInstance = Class.forName(resultType).newInstance();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                throw new RuntimeException(String.format("java中不能对%s进行实例化",resultType));
            }
            fun.setResultType(newInstance);
            fun.setSql(sql);
            list.add(fun);
        }
        mapper.setList(list);
        return mapper;
    }

    public Connection getConnection() throws Exception {
        Class.forName(dataBaseBean.getDriverClassName());
        Connection connection = DriverManager.getConnection(
                dataBaseBean.getUrl(), dataBaseBean.getUsername(),dataBaseBean.getPassword());
        // TODO 后期加入连接池
        return connection;
    }
}
