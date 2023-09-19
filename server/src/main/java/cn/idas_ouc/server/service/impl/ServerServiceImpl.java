package cn.idas_ouc.server.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.idas_ouc.common.utils.PageUtils;
import cn.idas_ouc.common.utils.Query;

import cn.idas_ouc.server.dao.ServerDao;
import cn.idas_ouc.server.entity.ServerEntity;
import cn.idas_ouc.server.service.ServerService;


@Service("serverService")
public class ServerServiceImpl extends ServiceImpl<ServerDao, ServerEntity> implements ServerService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ServerEntity> page = this.page(
                new Query<ServerEntity>().getPage(params),
                new QueryWrapper<ServerEntity>()
        );

        return new PageUtils(page);
    }

}