package cn.idas_ouc.server.controller;

import java.util.*;
import java.util.stream.Collectors;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import cn.idas_ouc.common.vo.MemberActiVo;
import cn.idas_ouc.server.feign.MemberFeignService;
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

    @Autowired
    private MemberFeignService memberFeignService;

    /**
     * 获得所有用户列表,并判断是否激活serveruser
     */
    @GetMapping("/useracti")
    //@RequiresPermissions("server:serveruser:list")
    public R useracti(@RequestParam Map<String, Object> params){
        // 1. 获得所有可以激活服务器的用户
        // 2. 查询 已经激活的所有用户列表
        List<ServerUserEntity> userEntities = serverUserService.list();
        Set<Long> idSet = userEntities.stream().map(item -> {
            return item.getUserId();
        }).collect(Collectors.toSet());
        userEntities.forEach(System.out::println);
        // 3. 对更新所有可激活用户列表中的，激活状态
        List<MemberActiVo> actiVos = memberFeignService.useracti(params);
        actiVos.forEach(System.out::println);
        actiVos = actiVos.stream().map(item -> {
            if (idSet.contains(item.getId())) {
                item.setActi_status(1);
            } else {
                item.setActi_status(0);
            }
            return item;
        }).collect(Collectors.toList());
        actiVos.forEach(System.out::println);
        PageUtils page = new PageUtils(actiVos,actiVos.size(),500,1);
        return R.ok().put("useracti", actiVos);
    }

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
