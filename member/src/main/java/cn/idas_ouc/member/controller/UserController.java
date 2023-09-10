package cn.idas_ouc.member.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import cn.idas_ouc.member.vo.UserVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.idas_ouc.member.entity.UserEntity;
import cn.idas_ouc.member.service.UserService;
import cn.idas_ouc.common.utils.PageUtils;
import cn.idas_ouc.common.utils.R;



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
    @PostMapping(value = "/login")
    R login(UserVo userVo){
        userVo.setRoles(Arrays.asList("admin"));
        userVo.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        userVo.setName("admin");
        userVo.setIntroduction("I am a super administrator");
        userVo.setToken("admin-token");
        System.out.println(userVo);

        // return R.ok().setData(userVo);
        return R.ok().put("token","admin-token").put("data",userVo);
    }

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("info")
    public R info() {
        Map<String, Object> map = new HashMap<>();
        map.put("roles",Arrays.asList("admin"));
        map.put("name","admin");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
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
