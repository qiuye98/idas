package cn.idas_ouc.member.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * ClassName: UserVo
 * Package: cn.idas_ouc.member.vo
 *
 * @Author boxin
 * @Create 2023/9/6 21:22
 * @Version 1.0
 * @Description:
 */
@Data
@ToString
public class UserVo {
    String username;

    String password;

    List<String> roles;

    String introduction;

    String avatar;

    String name;

    String token;

}
