package cn.idas_ouc.server.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.idas_ouc.common.utils.PageUtils;
import cn.idas_ouc.common.utils.Query;

import cn.idas_ouc.server.dao.ServerAccountDao;
import cn.idas_ouc.server.entity.ServerAccountEntity;
import cn.idas_ouc.server.service.ServerAccountService;


@Service("serverAccountService")
public class ServerAccountServiceImpl extends ServiceImpl<ServerAccountDao, ServerAccountEntity> implements ServerAccountService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ServerAccountEntity> page = this.page(
                new Query<ServerAccountEntity>().getPage(params),
                new QueryWrapper<ServerAccountEntity>()
        );

        return new PageUtils(page);
    }

}