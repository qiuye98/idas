package cn.idas_ouc.member.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.idas_ouc.member.entity.UserRoleEntity;
import cn.idas_ouc.member.service.UserRoleService;
import cn.idas_ouc.common.utils.PageUtils;
import cn.idas_ouc.common.utils.R;



/**
 * 用户角色
 *
 * @author wuboxin
 * @email wuboxin98@outlook.com
 * @date 2023-09-04 22:28:16
 */
@RestController
@RequestMapping("member/userrole")
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;

    /**
     * 列表
     */
    @GetMapping("/list")
    //@RequiresPermissions("member:userrole:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userRoleService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    //@RequiresPermissions("member:userrole:info")
    public R info(@PathVariable("id") Long id){
		UserRoleEntity userRole = userRoleService.getById(id);

        return R.ok().put("userRole", userRole);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("member:userrole:save")
    public R save(@RequestBody UserRoleEntity userRole){
		userRoleService.save(userRole);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    //@RequiresPermissions("member:userrole:update")
    public R update(@RequestBody UserRoleEntity userRole){
		userRoleService.updateById(userRole);

        return R.ok();
    }

    /**
     * 删除
     */
    @GetMapping("/delete")
    //@RequiresPermissions("member:userrole:delete")
    public R delete(@RequestBody Long[] ids){
		userRoleService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
