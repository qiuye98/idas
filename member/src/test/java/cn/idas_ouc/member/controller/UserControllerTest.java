package cn.idas_ouc.member.controller;

import cn.idas_ouc.common.vo.MemberActiVo;
import cn.idas_ouc.member.MemberApplication;
import cn.idas_ouc.member.entity.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * ClassName: UserControllerTest
 * Package: cn.idas_ouc.member.controller
 *
 * @Author boxin
 * @Create 2023/9/19 11:29
 * @Version 1.0
 * @Description:
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Test
    public void useracti() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(412L);
        userEntity.setName("dab1");
        userEntity.setDescription("big");
        MemberActiVo actiVo = new MemberActiVo();
        BeanUtils.copyProperties(userEntity,actiVo);
        System.out.println(userEntity);
        System.out.println(actiVo);

    }
}