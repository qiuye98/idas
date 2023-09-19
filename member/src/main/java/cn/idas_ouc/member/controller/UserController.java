package cn.idas_ouc.member.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import cn.idas_ouc.common.exception.GuiguException;
import cn.idas_ouc.common.helper.JwtHelper;
import cn.idas_ouc.common.utils.MD5;
import cn.idas_ouc.common.vo.MemberActiVo;
import cn.idas_ouc.member.service.MenuService;
import cn.idas_ouc.member.vo.LoginVo;
import cn.idas_ouc.member.vo.RouterVo;
import cn.idas_ouc.member.vo.UserVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.idas_ouc.member.entity.UserEntity;
import cn.idas_ouc.member.service.UserService;
import cn.idas_ouc.common.utils.PageUtils;
import cn.idas_ouc.common.utils.R;

import javax.servlet.http.HttpServletRequest;


/**
 * 用户表
 *
 * @author wuboxin
 * @email wuboxin98@outlook.com
 * @date 2023-09-04 22:28:16
 */
@RestController
@RequestMapping("member/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    // 获得所有需要激活的用户
    @GetMapping("useracti")
    public List<MemberActiVo> useracti(){
        List<UserEntity> userEntities = userService.list();
        userEntities.forEach(System.out::println);
        List<MemberActiVo> memberActiVos = userEntities.stream().map(userEntity -> {
            MemberActiVo memberActiVo = new MemberActiVo();
            BeanUtils.copyProperties(userEntity, memberActiVo);
            // todo 获得年纪、学位信息
            return memberActiVo;
        }).collect(Collectors.toList());
        memberActiVos.forEach(System.out::println);
        return memberActiVos;
    }

    @ApiOperation(value = "更新状态")
    @GetMapping("updateStatus/{id}/{status}")
    public R updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setStatus(status);
        userService.updateById(userEntity);
        return R.ok();
    }

    // 删除某用户
    @DeleteMapping("/remove/{id}")
    //@RequiresPermissions("member:user:list")
    public R remove(@PathVariable(value = "id") Long id){
        userService.removeByIds(Arrays.asList(id));
        return R.ok();
    }

    // 登录
/*     @PostMapping(value = "/login")
    R login(UserVo userVo){
        userVo.setRoles(Arrays.asList("admin"));
        userVo.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        userVo.setName("admin");
        userVo.setIntroduction("I am a super administrator");
        userVo.setToken("admin-token");
        System.out.println(userVo);

        // return R.ok().setData(userVo);
        return R.ok().put("token","admin-token").put("data",userVo);
    } */

    @ApiOperation(value = "登录")
    @PostMapping("login")
    public R login(@RequestBody LoginVo loginVo) {
        UserEntity sysUser = userService.getOne(new LambdaQueryWrapper<UserEntity>().eq(UserEntity::getUsername,loginVo.getUsername()) );
        if(null == sysUser) {
            throw new GuiguException(201,"用户不存在");
        }
        System.out.println("加密后密码："+MD5.encrypt(loginVo.getPassword()));
        System.out.println("数据库密码："+sysUser.getPassword());
        System.out.println(sysUser);

        if(!MD5.encrypt(loginVo.getPassword()).equals(sysUser.getPassword())) {
            throw new GuiguException(201,"密码错误");
        }
        if(sysUser.getStatus().intValue() == 0) {
            throw new GuiguException(201,"用户被禁用");
        }

        String token = JwtHelper.createToken(sysUser.getId(), sysUser.getUsername());

        Map<String, Object> map = userService.getUserInfo(loginVo.getUsername());
        map.put("token", token);
        return R.ok().put("data",map);
    }

    /**
     * 获取用户信息
     * @return
     */
/*     @GetMapping("info")
    public R info() {
        Map<String, Object> map = new HashMap<>();
        map.put("roles",Arrays.asList("admin"));
        map.put("name","admin");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return R.ok().put("data",map);
    } */

    @ApiOperation(value = "获取用户信息")
    @GetMapping("info")
    public R info(HttpServletRequest request) {
        String token = request.getHeader("token");
        String username = JwtHelper.getUsername(token);

        Map<String, Object> map = userService.getUserInfo(username);

        return R.ok().put("data",map);
    }

    /**
     * 退出
     * @return
     */
    @PostMapping("logout")
    public R logout(){
        return R.ok();
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    //@RequiresPermissions("member:user:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    //@RequiresPermissions("member:user:info")
    public R info(@PathVariable("id") Long id){
		UserEntity user = userService.getById(id);

        return R.ok().put("user", user);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("member:user:save")
    public R save(@RequestBody UserEntity user){
		userService.save(user);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    //@RequiresPermissions("member:user:update")
    public R update(@RequestBody UserEntity user){
		userService.updateById(user);

        return R.ok();
    }

    /**
     * 删除
     */
    @GetMapping("/delete")
    //@RequiresPermissions("member:user:delete")
    public R delete(@RequestBody Long[] ids){
		userService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
