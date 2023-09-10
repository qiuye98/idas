package cn.idas_ouc.member.helper;

import cn.idas_ouc.member.entity.MenuEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: MenuHelper
 * Package: cn.idas_ouc.member.helper
 *
 * @Author boxin
 * @Create 2023/9/10 15:13
 * @Version 1.0
 * @Description:
 */
public class MenuHelper {


    /**
     * 使用递归方法建菜单
     * @param sysMenuList
     * @return
     */
    public static List<MenuEntity> buildTree(List<MenuEntity> sysMenuList) {
        List<MenuEntity> trees = new ArrayList<>();
        for (MenuEntity sysMenu : sysMenuList) {
            if (sysMenu.getParentId().longValue() == 0) {
                trees.add(findChildren(sysMenu,sysMenuList));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     * @param treeNodes
     * @return
     */
    public static MenuEntity findChildren(MenuEntity sysMenu, List<MenuEntity> treeNodes) {
        sysMenu.setChildren(new ArrayList<MenuEntity>());

        for (MenuEntity it : treeNodes) {
            if(sysMenu.getId().longValue() == it.getParentId().longValue()) {
                if (sysMenu.getChildren() == null) {
                    sysMenu.setChildren(new ArrayList<>());
                }
                sysMenu.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return sysMenu;
    }
}
