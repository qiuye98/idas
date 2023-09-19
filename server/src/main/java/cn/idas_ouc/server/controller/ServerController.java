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


import cn.idas_ouc.server.entity.ServerEntity;
import cn.idas_ouc.server.service.ServerService;
import cn.idas_ouc.common.utils.PageUtils;
import cn.idas_ouc.common.utils.R;



/**
 * 服务器表
 *
 * @author wuboxin
 * @email wuboxin98@outlook.com
 * @date 2023-09-18 21:51:43
 */
@RestController
@RequestMapping("server/server")
public class ServerController {
    @Autowired
    private ServerService serverService;

    /**
     * 列表
     */
    @GetMapping("/list")
    //@RequiresPermissions("server:server:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = serverService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    //@RequiresPermissions("server:server:info")
    public R info(@PathVariable("id") Long id){
		ServerEntity server = serverService.getById(id);

        return R.ok().put("server", server);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("server:server:save")
    public R save(@RequestBody ServerEntity server){
		serverService.save(server);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    //@RequiresPermissions("server:server:update")
    public R update(@RequestBody ServerEntity server){
		serverService.updateById(server);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    //@RequiresPermissions("server:server:delete")
    public R delete(@RequestBody Long[] ids){
		serverService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
