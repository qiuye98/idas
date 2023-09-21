package cn.idas_ouc.server.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import cn.idas_ouc.server.entity.ServerEntity;
import cn.idas_ouc.server.entity.ServerUserEntity;
import cn.idas_ouc.server.service.ServerService;
import cn.idas_ouc.server.service.ServerUserService;
import cn.idas_ouc.server.util.ExecuteShellUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
@Slf4j
@RestController
@RequestMapping("server/serveraccount")
public class ServerAccountController {
    @Autowired
    private ServerAccountService serverAccountService;

    @Autowired
    private ServerUserService serverUserService;
    @Autowired
    private ServerService serverService;


    @Transactional
    @PostMapping("/useradd")
    //@RequiresPermissions("server:serveraccount:save")
    public R useradd(@RequestBody Map<String, String> map){
        // 获得关键id，查数据库是否存在
        Integer userId = Integer.valueOf(map.get("userId"));
        Integer serverId = Integer.valueOf(map.get("serverId"));
        String username = map.get("username");

        ServerAccountEntity accountEntity = serverAccountService.getOne(new QueryWrapper<ServerAccountEntity>()
                .select("id")
                .eq("user_id", userId)
                .eq("server_id", serverId));
        if ( accountEntity != null)
            return R.error(52000,username + " 用户已存在");

        // 获得用户信息
        String password = map.get("password");
        String uid = map.get("uid");
        // 获得服务器信息
        ServerEntity serverEntity = serverService.getById(serverId);
        log.info(serverEntity.toString());


        String admin = serverEntity.getAdmin();
        String adminPassword = serverEntity.getPassword();
        String ip = serverEntity.getSshOutIp();
        Integer port = Integer.valueOf(serverEntity.getSshOutPort());
        String serverCode = serverEntity.getCode();

        // 尝试创建用户
        ExecuteShellUtil executeShellUtil = new ExecuteShellUtil(ip,admin,adminPassword,port);
        String[] adduserCommands = {"sudo useradd -u "+ uid +" -m "+username};
        String result = executeShellUtil.executeForResult(adduserCommands);
        log.info(result);
        // 保存数据库
        ServerAccountEntity serverAccountEntity = new ServerAccountEntity();
        serverAccountEntity.setServerId(Long.valueOf(serverId));
        serverAccountEntity.setUserId(Long.valueOf(userId));
        serverAccountEntity.setStatus(1);
        serverAccountEntity.setSudo(0);
        serverAccountService.save(serverAccountEntity);
        if (result.contains("already exists")) {
            log.info(username + " 用户已存在");
            executeShellUtil.close();
            return R.ok(String.format("%s用户再服务器%s上已有账号“,username,serverCode"));
        }else{
            log.info(username + " 用户不存在");
            // 修改密码
            String[] chpwCommands = {"sudo su root","echo \""+username+":idas123\" | chpasswd","exit"};
            executeShellUtil.executeForResult(adduserCommands);
            executeShellUtil.close();
        }

        return R.ok(String.format("%s用户再服务器%s上创建账号成功，默认密码为%s",username,serverCode,password));
    }
    @GetMapping("/accounttable")
    //@RequiresPermissions("server:serveraccount:list")
    public R getUserServerAccountList(@RequestParam Map<String, Object> params){
        PageUtils page = serverAccountService.queryPage(params);

        List<ServerUserEntity> userList = serverUserService.list();
        List<ServerEntity> serverList = serverService.list();
        List<ServerAccountEntity> accountList = serverAccountService.list();

        R r = R.ok().put("userList", userList);
        r.put("serverList", serverList);
        r.put("accountList", accountList);
        r.put("page", page);
        return r;
    }

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
