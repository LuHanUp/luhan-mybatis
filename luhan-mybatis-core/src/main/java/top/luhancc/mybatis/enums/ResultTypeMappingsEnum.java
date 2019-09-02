package top.luhancc.mybatis.enums;

import com.sun.org.apache.regexp.internal.RE;

/**
 * 〈Mapper.xml中resultType和java类的映射〉<br>
 *
 * @author luHan
 * @create 2019-09-02 13:52
 * @since 1.0.0
 */
public enum ResultTypeMappingsEnum {
    STRING("string","java.lang.String"),
    INTEGER("Integer","java.lang.Integer"),
    INT("int","java.lang.Integer")
    ;
    private String keyword;
    private String className;

    ResultTypeMappingsEnum(String keyword, String className) {
        this.keyword = keyword;
        this.className = className;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getClassName() {
        return className;
    }

    public static ResultTypeMappingsEnum get(String name){
        for (ResultTypeMappingsEnum resultTypeMappingsEnum : ResultTypeMappingsEnum.values()) {
            if(resultTypeMappingsEnum.keyword.equalsIgnoreCase(name)){
                return resultTypeMappingsEnum;
            }
        }
        return null;
    }
}
