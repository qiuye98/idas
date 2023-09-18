package cn.idas_ouc.member.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import cn.idas_ouc.member.vo.AssginRoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.idas_ouc.member.entity.RoleEntity;
import cn.idas_ouc.member.service.RoleService;
import cn.idas_ouc.common.utils.PageUtils;
import cn.idas_ouc.common.utils.R;



/**
 * 角色
 *
 * @author wuboxin
 * @email wuboxin98@outlook.com
 * @date 2023-09-04 22:28:16
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("member/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public R toAssign(@PathVariable Long userId) {
        Map<String, Object> roleMap = roleService.findRoleByUserId(userId);
        return R.ok(roleMap);
    }

    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    public R doAssign(@RequestBody AssginRoleVo assginRoleVo) {
        roleService.doAssign(assginRoleVo);
        return R.ok();
    }

    // 删除角色
    @DeleteMapping("/remove/{id}")
    //@RequiresPermissions("member:role:list")
    public R remove(@PathVariable(value = "id") Long id){
        roleService.removeByIds(Arrays.asList(id));
        return R.ok();
    }


    /**
     * 列表
     */
    @ApiOperation("查询所有角色")
    @GetMapping("/list")
    //@RequiresPermissions("member:role:list")
    public R list(@RequestParam Map<String, Object> params){
        System.out.println("list param: " +params);
        PageUtils page = roleService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    //@RequiresPermissions("member:role:info")
    public R info(@PathVariable("id") Long id){
		RoleEntity role = roleService.getById(id);

        return R.ok().put("role", role);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("member:role:save")
    public R save(@RequestBody RoleEntity role){
		roleService.save(role);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update",method = {RequestMethod.PUT,RequestMethod.POST})
    //@RequiresPermissions("member:role:update")
    public R update(@RequestBody RoleEntity role){
		roleService.updateById(role);

        return R.ok();
    }

    /**
     * 删除
     */
    @GetMapping("/delete")
    //@RequiresPermissions("member:role:delete")
    public R delete(@RequestBody Long[] ids){
		roleService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
