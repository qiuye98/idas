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


import cn.idas_ouc.server.entity.ServerAccountEntity;
import cn.idas_ouc.server.service.ServerAccountService;
import cn.idas_ouc.common.utils.PageUtils;
import cn.idas_ouc.common.utils.R;



/**
 * 服务器用户账户管理表
 *
 * @author wuboxin
 * @email wuboxin98@outlook.com
 * @date 2023-09-18 21:51:43
 */
@RestController
@RequestMapping("server/serveraccount")
public class ServerAccountController {
    @Autowired
    private ServerAccountService serverAccountService;

    /**
     * 列表
     */
    @GetMapping("/list")
    //@RequiresPermissions("server:serveraccount:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = serverAccountService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    //@RequiresPermissions("server:serveraccount:info")
    public R info(@PathVariable("id") Long id){
		ServerAccountEntity serverAccount = serverAccountService.getById(id);

        return R.ok().put("serverAccount", serverAccount);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("server:serveraccount:save")
    public R save(@RequestBody ServerAccountEntity serverAccount){
		serverAccountService.save(serverAccount);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    //@RequiresPermissions("server:serveraccount:update")
    public R update(@RequestBody ServerAccountEntity serverAccount){
		serverAccountService.updateById(serverAccount);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    //@RequiresPermissions("server:serveraccount:delete")
    public R delete(@RequestBody Long[] ids){
		serverAccountService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
