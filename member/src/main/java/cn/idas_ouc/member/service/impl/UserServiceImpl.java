package cn.idas_ouc.member.service.impl;

import cn.idas_ouc.member.service.MenuService;
import cn.idas_ouc.member.vo.RouterVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.idas_ouc.common.utils.PageUtils;
import cn.idas_ouc.common.utils.Query;

import cn.idas_ouc.member.dao.UserDao;
import cn.idas_ouc.member.entity.UserEntity;
import cn.idas_ouc.member.service.UserService;
import org.springframework.util.StringUtils;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Autowired
    private MenuService sysMenuService;

    @Autowired
    UserDao userDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserEntity> page = this.page(
                new Query<UserEntity>().getPage(params),
                new QueryWrapper<UserEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public Map<String, Object> getUserInfo(String username) {
        Map<String, Object> result = new HashMap<>();
        if( username != null && StringUtils.isEmpty(username)){
            username = "wuboxin";
        }
        UserEntity sysUser = userDao.selectOne(
                new LambdaQueryWrapper<UserEntity>().eq(UserEntity::getUsername,username) );
        System.out.println("sysUser: "+sysUser);
        //根据用户id获取菜单权限值
        List<RouterVo> routerVoList = sysMenuService.findUserMenuListByUserId(sysUser.getId());
        //根据用户id获取用户按钮权限
        List<String> permsList = sysMenuService.findUserPermsList(sysUser.getId());

        result.put("name", sysUser.getName());
        result.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        //当前权限控制使用不到，我们暂时忽略
        result.put("roles",  new HashSet<>());
        result.put("buttons", permsList);
        result.put("routers", routerVoList);
        return result;
    }

}