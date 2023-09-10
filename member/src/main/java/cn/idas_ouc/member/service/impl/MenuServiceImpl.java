package cn.idas_ouc.member.service.impl;

import cn.idas_ouc.common.exception.GuiguException;
import cn.idas_ouc.member.dao.RoleMenuDao;
import cn.idas_ouc.member.entity.RoleMenuEntity;
import cn.idas_ouc.member.helper.MenuHelper;
import cn.idas_ouc.member.vo.AssginMenuVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.idas_ouc.common.utils.PageUtils;
import cn.idas_ouc.common.utils.Query;

import cn.idas_ouc.member.dao.MenuDao;
import cn.idas_ouc.member.entity.MenuEntity;
import cn.idas_ouc.member.service.MenuService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuDao, MenuEntity> implements MenuService {


    @Autowired
    private MenuDao menuDao;
    @Autowired
    private RoleMenuDao roleMenuDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MenuEntity> page = this.page(
                new Query<MenuEntity>().getPage(params),
                new QueryWrapper<MenuEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<MenuEntity> findNodes() {
        //全部权限列表
        List<MenuEntity> menuList = this.list();
        if (CollectionUtils.isEmpty(menuList)) return null;

        //构建树形数据
        List<MenuEntity> result = MenuHelper.buildTree(menuList);
        return result;
    }

    @Override
    public List<MenuEntity> findSysMenuByRoleId(Long roleId) {
        //全部权限列表
        List<MenuEntity> allSysMenuList = this.list(
                new LambdaQueryWrapper<MenuEntity>().eq(MenuEntity::getStatus, 1));

        //根据角色id获取角色权限
        List<RoleMenuEntity> sysRoleMenuList =
                roleMenuDao.selectList(new LambdaQueryWrapper<RoleMenuEntity>().
                        eq(RoleMenuEntity::getRoleId, roleId));
        //转换给角色id与角色权限对应Map对象
        List<Long> menuIdList = sysRoleMenuList.stream().map(e -> e.getMenuId()).collect(Collectors.toList());

        allSysMenuList.forEach(permission -> {
            if (menuIdList.contains(permission.getId())) {
                permission.setSelect(true);
            } else {
                permission.setSelect(false);
            }
        });

        List<MenuEntity> sysMenuList = MenuHelper.buildTree(allSysMenuList);
        return sysMenuList;
    }

    @Transactional
    @Override
    public void doAssign(AssginMenuVo assginMenuVo) {
        roleMenuDao.delete(new LambdaQueryWrapper<RoleMenuEntity>().eq(RoleMenuEntity::getRoleId, assginMenuVo.getRoleId()));

        for (Long menuId : assginMenuVo.getMenuIdList()) {
            if (StringUtils.isEmpty(menuId)) continue;
            RoleMenuEntity rolePermission = new RoleMenuEntity();
            rolePermission.setRoleId(assginMenuVo.getRoleId());
            rolePermission.setMenuId(menuId);
            roleMenuDao.insert(rolePermission);
        }
    }


    @Override
    public boolean removeById(Serializable id) {
        int count = this.count(new LambdaQueryWrapper<MenuEntity>().eq(MenuEntity::getParentId, id));
        if (count > 0) {
            throw new GuiguException(201,"菜单不能删除");
        }
        menuDao.deleteById(id);
        return false;
    }
}