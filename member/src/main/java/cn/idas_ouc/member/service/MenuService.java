package cn.idas_ouc.member.service;

import cn.idas_ouc.member.vo.AssginMenuVo;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.idas_ouc.common.utils.PageUtils;
import cn.idas_ouc.member.entity.MenuEntity;

import java.util.List;
import java.util.Map;

/**
 * 菜单表
 *
 * @author wuboxin
 * @email wuboxin98@outlook.com
 * @date 2023-09-04 22:28:16
 */
public interface MenuService extends IService<MenuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 菜单树形数据
     * @return
     */
    List<MenuEntity> findNodes();

    /**
     * 根据角色获取授权权限数据
     * @return
     */
    List<MenuEntity> findSysMenuByRoleId(Long roleId);

    /**
     * 保存角色权限
     * @param  assginMenuVo
     */
    void doAssign(AssginMenuVo assginMenuVo);
}

