package cn.idas_ouc.common.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: MemberActiVo
 * Package: cn.idas_ouc.common.vo
 *
 * @Author boxin
 * @Create 2023/9/19 10:22
 * @Version 1.0
 * @Description:
 */


@Data
public class MemberActiVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 成员id
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 姓名
     */
    private String name;

    // 入学年份
    // todo
    private String enroll;


    // 学位
    // todo
    private String degree;

    /**
     * 状态（1：正常 0：未开通）
     * server 判断
     */
    private Integer acti_status;


}
