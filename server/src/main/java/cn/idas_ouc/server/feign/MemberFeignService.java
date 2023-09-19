package cn.idas_ouc.server.feign;

import cn.idas_ouc.common.utils.R;
import cn.idas_ouc.common.vo.MemberActiVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * ClassName: MemberFeignService
 * Package: cn.idas_ouc.server.feign
 *
 * @Author boxin
 * @Create 2023/9/19 10:17
 * @Version 1.0
 * @Description:
 */

@FeignClient("member" )
public interface MemberFeignService {


    // 获得所有需要激活的用户
    @GetMapping("/member/user/useracti")
    public List<MemberActiVo> useracti();
}
