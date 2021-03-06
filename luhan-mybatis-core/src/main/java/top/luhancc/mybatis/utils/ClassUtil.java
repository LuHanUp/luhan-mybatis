package top.luhancc.mybatis.utils;

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.ParameterizedType;

/**
 * 〈class工具类〉<br>
 *
 * @author luHan
 * @create 2019-09-02 11:27
 * @since 1.0.0
 */
public class ClassUtil {

    /**
     * 得到一个类泛型的真实类型
     */
    public static Class getRealType(Class<?> clazz){
        // 获取当前new的对象的泛型的父类类型
        ParameterizedType pt = (ParameterizedType) clazz.getGenericSuperclass();
        // 获取第一个类型参数的真实类型
        if(pt.getActualTypeArguments()[0] instanceof ParameterizedTypeImpl){
            ParameterizedTypeImpl parameterizedType = (ParameterizedTypeImpl) pt.getActualTypeArguments()[0];
            return parameterizedType.getRawType();
        }
        return (Class) pt.getActualTypeArguments()[0];
    }
}
