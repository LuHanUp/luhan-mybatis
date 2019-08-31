package top.luhancc.mybatis.example;


import top.luhancc.mybatis.core.SQLSession;
import top.luhancc.mybatis.example.entity.Users;
import top.luhancc.mybatis.example.mapper.UserMapper;

/**
 * 〈测试类〉<br>
 *
 * @author luHan
 * @create 2019-08-30 14:51
 * @since 1.0.0
 */
public class LuHanMybatisApplication {
    public static void main(String[] args) {
        SQLSession sqlsession = new SQLSession();
        UserMapper mapper = sqlsession.getMapper(UserMapper.class);
        Users user = mapper.selectById("1");
        Users user2 = mapper.selectByName("luhan");
        System.out.println(user);
        System.out.println(user2);
    }
}
