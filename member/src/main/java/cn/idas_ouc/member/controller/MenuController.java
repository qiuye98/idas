package cn.idas_ouc.member.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.idas_ouc.member.entity.MenuEntity;
import cn.idas_ouc.member.service.MenuService;
import cn.idas_ouc.common.utils.PageUtils;
import cn.idas_ouc.common.utils.R;



/**
 * 菜单表
 *
 * @author wuboxin
 * @email wuboxin98@outlook.com
 * @date 2023-09-04 22:28:16
 */
@RestController
@RequestMapping("member/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;



    @ApiOperation(value = "获取菜单")
    @GetMapping("findNodes")
    public R findNodes() {
        List<MenuEntity> list = menuService.findNodes();
        return R.ok().put("menuList",list);
    }

    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public R save(@RequestBody MenuEntity permission) {
        menuService.save(permission);
        return R.ok();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public R updateById(@RequestBody MenuEntity permission) {
        menuService.updateById(permission);
        return R.ok();
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable Long id) {
        menuService.removeById(id);
        return R.ok();
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    //@RequiresPermissions("member:menu:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = menuService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    //@RequiresPermissions("member:menu:info")
    public R info(@PathVariable("id") Long id){
		MenuEntity menu = menuService.getById(id);

        return R.ok().put("menu", menu);
    }

    /**
     * 保存
     */
    // @RequestMapping("/save")
    // //@RequiresPermissions("member:menu:save")
    // public R save(@RequestBody MenuEntity menu){
	// 	menuService.save(menu);
    //
    //     return R.ok();
    // }

    /**
     * 修改
     */
    @PostMapping("/update")
    //@RequiresPermissions("member:menu:update")
    public R update(@RequestBody MenuEntity menu){
		menuService.updateById(menu);

        return R.ok();
    }

    /**
     * 删除
     */
    @GetMapping("/delete")
    //@RequiresPermissions("member:menu:delete")
    public R delete(@RequestBody Long[] ids){
		menuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
