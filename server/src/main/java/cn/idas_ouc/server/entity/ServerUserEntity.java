package cn.idas_ouc.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;


import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 服务器用户信息表
 * 
 * @author wuboxin
 * @email wuboxin98@outlook.com
 * @date 2023-09-18 21:51:43
 */
@Data
@TableName("server_user")
public class ServerUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	@TableId(type = IdType.INPUT)
	private Long userId;
	/**
	 * 服务器统一uid
	 */
	private Integer uid;
	/**
	 * 服务器统一用户名
	 */
	private String username;
	/**
	 * 默认密码
	 */
	private String password;
	/**
	 * 服务器用户状态（1：正常 0：停用不维护）
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 删除标记（0:不可用 1:可用）
	 */
	@TableLogic //逻辑删除
	private Integer isDeleted;

}
