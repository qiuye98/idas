package cn.idas_ouc.server.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import cn.idas_ouc.server.entity.ServerUserEntity;
import cn.idas_ouc.server.service.ServerUserService;
import cn.idas_ouc.common.utils.PageUtils;
import cn.idas_ouc.common.utils.R;



/**
 * 服务器用户信息表
 *
 * @author wuboxin
 * @email wuboxin98@outlook.com
 * @date 2023-09-18 21:51:43
 */
@RestController
@RequestMapping("server/serveruser")
public class ServerUserController {
    @Autowired
    private ServerUserService serverUserService;

    /**
     * 列表
     */
    @GetMapping("/list")
    //@RequiresPermissions("server:serveruser:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = serverUserService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{userId}")
    //@RequiresPermissions("server:serveruser:info")
    public R info(@PathVariable("userId") Long userId){
		ServerUserEntity serverUser = serverUserService.getById(userId);

        return R.ok().put("serverUser", serverUser);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("server:serveruser:save")
    public R save(@RequestBody ServerUserEntity serverUser){
		serverUserService.save(serverUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    //@RequiresPermissions("server:serveruser:update")
    public R update(@RequestBody ServerUserEntity serverUser){
		serverUserService.updateById(serverUser);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    //@RequiresPermissions("server:serveruser:delete")
    public R delete(@RequestBody Long[] userIds){
		serverUserService.removeByIds(Arrays.asList(userIds));

        return R.ok();
    }

}
