package cn.idas_ouc.member.dao;

import cn.idas_ouc.member.entity.UserRoleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户角色
 * 
 * @author wuboxin
 * @email wuboxin98@outlook.com
 * @date 2023-09-04 22:28:16
 */
@Mapper
public interface UserRoleDao extends BaseMapper<UserRoleEntity> {
	
}
