package top.luhancc.mybatis.example;


import top.luhancc.mybatis.core.SQLSession;
import top.luhancc.mybatis.example.entity.Users;
import top.luhancc.mybatis.example.mapper.UserMapper;

import java.util.List;

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
    }
}
