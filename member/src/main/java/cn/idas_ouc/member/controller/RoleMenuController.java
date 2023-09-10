package cn.idas_ouc.member.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.idas_ouc.member.entity.RoleMenuEntity;
import cn.idas_ouc.member.service.RoleMenuService;
import cn.idas_ouc.common.utils.PageUtils;
import cn.idas_ouc.common.utils.R;



/**
 * 角色菜单
 *
 * @author wuboxin
 * @email wuboxin98@outlook.com
 * @date 2023-09-04 22:28:16
 */
@RestController
@RequestMapping("member/rolemenu")
public class RoleMenuController {
    @Autowired
    private RoleMenuService roleMenuService;

    /**
     * 列表
     */
    @GetMapping("/list")
    //@RequiresPermissions("member:rolemenu:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = roleMenuService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    //@RequiresPermissions("member:rolemenu:info")
    public R info(@PathVariable("id") Long id){
		RoleMenuEntity roleMenu = roleMenuService.getById(id);

        return R.ok().put("roleMenu", roleMenu);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("member:rolemenu:save")
    public R save(@RequestBody RoleMenuEntity roleMenu){
		roleMenuService.save(roleMenu);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    //@RequiresPermissions("member:rolemenu:update")
    public R update(@RequestBody RoleMenuEntity roleMenu){
		roleMenuService.updateById(roleMenu);

        return R.ok();
    }

    /**
     * 删除
     */
    @GetMapping("/delete")
    //@RequiresPermissions("member:rolemenu:delete")
    public R delete(@RequestBody Long[] ids){
		roleMenuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
