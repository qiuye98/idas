package cn.idas_ouc.member.service.impl;

import cn.idas_ouc.member.dao.UserRoleDao;
import cn.idas_ouc.member.entity.UserRoleEntity;
import cn.idas_ouc.member.vo.AssginRoleVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.idas_ouc.common.utils.PageUtils;
import cn.idas_ouc.common.utils.Query;

import cn.idas_ouc.member.dao.RoleDao;
import cn.idas_ouc.member.entity.RoleEntity;
import cn.idas_ouc.member.service.RoleService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleDao, RoleEntity> implements RoleService {

    @Autowired
    private UserRoleDao UserRoleMapper;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RoleEntity> page = this.page(
                new Query<RoleEntity>().getPage(params),
                new QueryWrapper<RoleEntity>()
        );

        return new PageUtils(page);
    }


    @Override
    public Map<String, Object> findRoleByUserId(Long userId) {
        //查询所有的角色
        List<RoleEntity> allRolesList = this.list();

        //拥有的角色id
        List<UserRoleEntity> existUserRoleList = UserRoleMapper.selectList(
                new LambdaQueryWrapper<UserRoleEntity>().
                        eq(UserRoleEntity::getUserId, userId).
                        select(UserRoleEntity::getRoleId));
        List<Long> existRoleIdList = existUserRoleList.stream().map(c -> c.getRoleId()).collect(Collectors.toList());

        //对角色进行分类
        List<RoleEntity> assginRoleList = new ArrayList<>();
        for (RoleEntity role : allRolesList) {
            //已分配
            if (existRoleIdList.contains(role.getId())) {
                assginRoleList.add(role);
            }
        }

        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("assginRoleList", assginRoleList);
        roleMap.put("allRolesList", allRolesList);
        return roleMap;
    }

    @Transactional
    @Override
    public void doAssign(AssginRoleVo assginRoleVo) {
        // 删除所有已有的
        UserRoleMapper.delete(new LambdaQueryWrapper<UserRoleEntity>().eq(UserRoleEntity::getUserId, assginRoleVo.getUserId()));
        // 重新添加返回的
        for (Long roleId : assginRoleVo.getRoleIdList()) {
            if (StringUtils.isEmpty(roleId)) continue;
            UserRoleEntity userRole = new UserRoleEntity();
            userRole.setUserId(assginRoleVo.getUserId());
            userRole.setRoleId(roleId);
            UserRoleMapper.insert(userRole);
        }
    }

}
