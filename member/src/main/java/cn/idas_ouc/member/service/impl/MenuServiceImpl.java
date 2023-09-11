package cn.idas_ouc.member.service.impl;

import cn.idas_ouc.common.exception.GuiguException;
import cn.idas_ouc.member.dao.RoleMenuDao;
import cn.idas_ouc.member.entity.RoleMenuEntity;
import cn.idas_ouc.member.helper.MenuHelper;
import cn.idas_ouc.member.vo.AssginMenuVo;
import cn.idas_ouc.member.vo.MetaVo;
import cn.idas_ouc.member.vo.RouterVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.LinkedList;
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
        List<MenuEntity> allMenuEntityList = this.list(
                new LambdaQueryWrapper<MenuEntity>().eq(MenuEntity::getStatus, 1));

        //根据角色id获取角色权限
        List<RoleMenuEntity> sysRoleMenuList =
                roleMenuDao.selectList(new LambdaQueryWrapper<RoleMenuEntity>().
                        eq(RoleMenuEntity::getRoleId, roleId));
        //转换给角色id与角色权限对应Map对象
        List<Long> menuIdList = sysRoleMenuList.stream().map(e -> e.getMenuId()).collect(Collectors.toList());

        allMenuEntityList.forEach(permission -> {
            if (menuIdList.contains(permission.getId())) {
                permission.setSelect(true);
            } else {
                permission.setSelect(false);
            }
        });

        List<MenuEntity> MenuEntityList = MenuHelper.buildTree(allMenuEntityList);
        return MenuEntityList;
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

    @Override
    public List<RouterVo> findUserMenuListByUserId(Long userId) {
        //超级管理员admin账号id为：1
        List<MenuEntity> MenuEntityList = null;
        if (userId.longValue() == 1) {
            MenuEntityList = this.list(new LambdaQueryWrapper<MenuEntity>().
                    eq(MenuEntity::getStatus, 1).orderByAsc(MenuEntity::getSortValue));
        } else {
            MenuEntityList = menuDao.findListByUserId(userId);
        }
        //构建树形数据
        List<MenuEntity> MenuEntityTreeList = MenuHelper.buildTree(MenuEntityList);

        List<RouterVo> routerVoList = this.buildMenus(MenuEntityTreeList);
        return routerVoList;
    }


    /**
     * 根据菜单构建路由
     * @param menus
     * @return
     */
    private List<RouterVo> buildMenus(List<MenuEntity> menus) {
        List<RouterVo> routers = new LinkedList<RouterVo>();
        for (MenuEntity menu : menus) {
            RouterVo router = new RouterVo();
            router.setHidden(false);
            router.setAlwaysShow(false);
            router.setPath(getRouterPath(menu));
            router.setComponent(menu.getComponent());
            router.setMeta(new MetaVo(menu.getName(), menu.getIcon()));
            List<MenuEntity> children = menu.getChildren();
            //如果当前是菜单，需将按钮对应的路由加载出来，如：“角色授权”按钮对应的路由在“系统管理”下面
            if(menu.getType().intValue() == 1) {
                List<MenuEntity> hiddenMenuList = children.stream().filter(item -> !StringUtils.isEmpty(item.getComponent())).collect(Collectors.toList());
                for (MenuEntity hiddenMenu : hiddenMenuList) {
                    RouterVo hiddenRouter = new RouterVo();
                    hiddenRouter.setHidden(true);
                    hiddenRouter.setAlwaysShow(false);
                    hiddenRouter.setPath(getRouterPath(hiddenMenu));
                    hiddenRouter.setComponent(hiddenMenu.getComponent());
                    hiddenRouter.setMeta(new MetaVo(hiddenMenu.getName(), hiddenMenu.getIcon()));
                    routers.add(hiddenRouter);
                }
            } else {
                if (!CollectionUtils.isEmpty(children)) {
                    if(children.size() > 0) {
                        router.setAlwaysShow(true);
                    }
                    router.setChildren(buildMenus(children));
                }
            }
            routers.add(router);
        }
        return routers;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(MenuEntity menu) {
        String routerPath = "/" + menu.getPath();
        if(menu.getParentId().intValue() != 0) {
            routerPath = menu.getPath();
        }
        return routerPath;
    }

    @Override
    public List<String> findUserPermsList(Long userId) {
        //超级管理员admin账号id为：1
        List<MenuEntity> sysMenuList = null;
        if (userId.longValue() == 1) {
            sysMenuList = this.list(new LambdaQueryWrapper<MenuEntity>().eq(MenuEntity::getStatus, 1));
        } else {
            sysMenuList = menuDao.findListByUserId(userId);
        }
        List<String> permsList = sysMenuList.stream().filter(item -> item.getType() == 2).map(item -> item.getPerms()).collect(Collectors.toList());
        return permsList;
    }
}