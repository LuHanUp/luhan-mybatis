package top.luhancc.mybatis.configbean;

import lombok.Data;

import java.util.List;

/**
 * 〈Mapper封装的bean 包含接口名称，Mapper下所有的方法〉<br>
 *
 * @author luHan
 * @create 2019-08-30 13:56
 * @since 1.0.0
 */
@Data
public class MapperBean {
    private String interfaceName; // 接口名
    private String mapperXmlName; // mapper.xml文件名称
    private List<FunctionBean> list; // 接口下所有方法
}
