package cn.idas_ouc.server.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.idas_ouc.common.utils.PageUtils;
import cn.idas_ouc.common.utils.Query;

import cn.idas_ouc.server.dao.ServerUserDao;
import cn.idas_ouc.server.entity.ServerUserEntity;
import cn.idas_ouc.server.service.ServerUserService;


@Service("serverUserService")
public class ServerUserServiceImpl extends ServiceImpl<ServerUserDao, ServerUserEntity> implements ServerUserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ServerUserEntity> page = this.page(
                new Query<ServerUserEntity>().getPage(params),
                new QueryWrapper<ServerUserEntity>()
        );

        return new PageUtils(page);
    }

}