package cn.idas_ouc.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.idas_ouc.common.utils.PageUtils;
import cn.idas_ouc.server.entity.ServerUserEntity;

import java.util.Map;

/**
 * 服务器用户信息表
 *
 * @author wuboxin
 * @email wuboxin98@outlook.com
 * @date 2023-09-18 21:51:43
 */
public interface ServerUserService extends IService<ServerUserEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

