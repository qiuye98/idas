package cn.idas_ouc.member.dao;

import cn.idas_ouc.member.entity.RoleEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ClassName: UserDaoTest
 * Package: cn.idas_ouc.member.dao
 *
 * @Author boxin
 * @Create 2023/9/5 0:17
 * @Version 1.0
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class UserDaoTest {



    @Autowired
    private RoleDao roleMapper;

    @Test
    public void testSelectList() {
        System.out.println(("----- selectAll method test ------"));
        //UserMapper 中的 selectList() 方法的参数为 MP 内置的条件封装器 Wrapper
        //所以不填写就是无任何条件
        List<RoleEntity> users = roleMapper.selectList(null);
        users.forEach(System.out::println);
    }

}