package cn.idas_ouc.member.service.impl;

import cn.idas_ouc.common.exception.GuiguException;
import cn.idas_ouc.member.helper.MenuHelper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.idas_ouc.common.utils.PageUtils;
import cn.idas_ouc.common.utils.Query;

import cn.idas_ouc.member.dao.MenuDao;
import cn.idas_ouc.member.entity.MenuEntity;
import cn.idas_ouc.member.service.MenuService;


@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuDao, MenuEntity> implements MenuService {


    @Autowired
    private MenuDao menuDao;

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
    public boolean removeById(Serializable id) {
        int count = this.count(new LambdaQueryWrapper<MenuEntity>().eq(MenuEntity::getParentId, id));
        if (count > 0) {
            throw new GuiguException(201,"菜单不能删除");
        }
        menuDao.deleteById(id);
        return false;
    }
}