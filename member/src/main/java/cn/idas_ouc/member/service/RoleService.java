package cn.idas_ouc.member.service;

import cn.idas_ouc.member.vo.AssginRoleVo;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.idas_ouc.common.utils.PageUtils;
import cn.idas_ouc.member.entity.RoleEntity;

import java.util.Map;

/**
 * 角色
 *
 * @author wuboxin
 * @email wuboxin98@outlook.com
 * @date 2023-09-04 22:28:16
 */
public interface RoleService extends IService<RoleEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据用户获取角色数据
     * @param userId
     * @return
     */
    Map<String, Object> findRoleByUserId(Long userId);

    /**
     * 分配角色
     * @param assginRoleVo
     */
    void doAssign(AssginRoleVo assginRoleVo);
}

