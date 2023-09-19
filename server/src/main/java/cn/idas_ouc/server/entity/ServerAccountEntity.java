package cn.idas_ouc.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;


import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 服务器用户账户管理表
 * 
 * @author wuboxin
 * @email wuboxin98@outlook.com
 * @date 2023-09-18 21:51:43
 */
@Data
@TableName("server_account")
public class ServerAccountEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 服务器id
	 */
	private Long serverId;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * sudo状态（1：开启 0：停用）
	 */
	private Integer sudo;
	/**
	 * 用户的单个服务器账户状态（1：正常 0：停用）
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
