package cn.idas_ouc.member.service;

import cn.idas_ouc.member.dao.UserDao;
import cn.idas_ouc.member.entity.UserEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * ClassName: UserServiceTest
 * Package: cn.idas_ouc.member.service
 *
 * @Author boxin
 * @Create 2023/9/10 23:22
 * @Version 1.0
 * @Description:
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserDao userDao;
    @Autowired
    UserService userService;

    @Test
    public void getUserInfo() {
        UserEntity sysUser = userService.getOne(
                new LambdaQueryWrapper<UserEntity>().eq(UserEntity::getUsername,"wuboxin") );
        UserEntity sysUser1 = userDao.selectOne(
                new LambdaQueryWrapper<UserEntity>().eq(UserEntity::getUsername,"wuboxin") );
        System.out.println(sysUser);
        System.out.println(sysUser1);

    }
}