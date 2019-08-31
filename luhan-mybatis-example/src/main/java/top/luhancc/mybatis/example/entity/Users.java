package top.luhancc.mybatis.example.entity;

import lombok.Data;

/**
 * 〈数据库中的User表〉<br>
 *
 * @author luHan
 * @create 2019-08-30 13:50
 * @since 1.0.0
 */
@Data
public class Users {
    private String id;
    private String username;
    private String password;
}
