package cn.idas_ouc.server.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;


import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 服务器表
 * 
 * @author wuboxin
 * @email wuboxin98@outlook.com
 * @date 2023-09-18 21:51:43
 */
@Data
@TableName("server")
public class ServerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 服务器id
	 */
	@TableId
	private Long id;
	/**
	 * 服务器编码
	 */
	private String code;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 管理员用户名
	 */
	private String admin;
	/**
	 * 管理员密码
	 */
	private String password;
	/**
	 * 校内ssh ip
	 */
	private String sshSchIp;
	/**
	 * 校内ssh port
	 */
	private String sshSchPort;
	/**
	 * 校外ssh ip
	 */
	private String sshOutIp;
	/**
	 * 校外ssh port
	 */
	private String sshOutPort;
	/**
	 * 服务器状态（1：正常 0：停用）
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
