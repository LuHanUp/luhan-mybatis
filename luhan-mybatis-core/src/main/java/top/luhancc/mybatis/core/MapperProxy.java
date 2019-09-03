package top.luhancc.mybatis.core;

import top.luhancc.mybatis.configbean.FunctionBean;
import top.luhancc.mybatis.configbean.MapperBean;
import top.luhancc.mybatis.excutor.SQLExcutor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 〈Mapper类的代理类〉<br>
 *
 * @author luHan
 * @create 2019-08-30 14:47
 * @since 1.0.0
 */
public class MapperProxy implements InvocationHandler {
    private SQLExcutor excutor;
    private SQLSession sqlSession;
    private DataSourceConfiguration dataSourceConfiguration;

    public MapperProxy(SQLExcutor excutor, SQLSession sqlSession, DataSourceConfiguration dataSourceConfiguration) {
        this.excutor = excutor;
        this.sqlSession = sqlSession;
        this.dataSourceConfiguration = dataSourceConfiguration;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MapperBean readMapper = dataSourceConfiguration.readMapper(method.getDeclaringClass().getName());
        // 是否是xml文件对应的接口
        if(!method.getDeclaringClass().getName().equals(readMapper.getInterfaceName())){
            throw new RuntimeException(String.format("%s文件namespace对应接口{%s}错误",readMapper.getMapperXmlName(),method.getDeclaringClass().getName()));
        }
        List<FunctionBean> list = readMapper.getList();
        if(null != list || 0 != list.size()){
            for (FunctionBean function : list) {
                // id是否和接口方法名一样
                if(method.getName().equals(function.getFuncName())){
                    return excutor.excutor(function.getSql(), args,method.getReturnType(),function.getResultType());
                }
            }
        }
        return null;
    }
}
