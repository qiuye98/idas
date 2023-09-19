package cn.idas_ouc.server.dao;

import cn.idas_ouc.server.entity.ServerAccountEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 服务器用户账户管理表
 * 
 * @author wuboxin
 * @email wuboxin98@outlook.com
 * @date 2023-09-18 21:51:43
 */
@Mapper
public interface ServerAccountDao extends BaseMapper<ServerAccountEntity> {
	
}
