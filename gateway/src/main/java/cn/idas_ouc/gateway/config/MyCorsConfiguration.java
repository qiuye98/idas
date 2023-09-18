package cn.idas_ouc.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Collections;


/**
 * ClassName: CrosConfiguration
 * Package: com.atguigu.gulimall.gulimallgataway.config
 *
 * @Author boxin
 * @Create 2023/6/16 19:21
 * @Version 1.0
 * @Description:
 */
@Configuration
public class MyCorsConfiguration {

    // @Bean
    // public CorsWebFilter corsWebFilter(){
    //
    //     CorsConfiguration corsConfiguration = new CorsConfiguration();
    //     // 1. 配置跨域
    //     corsConfiguration.addAllowedHeader("*");
    //     corsConfiguration.addAllowedMethod("*");
    //     corsConfiguration.addAllowedOrigin("*");
    //     // corsConfiguration.setAllowedOriginPatterns(Collections.singletonList("*"));
    //     corsConfiguration.setAllowCredentials(true);
    //
    //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //     source.registerCorsConfiguration("/**", corsConfiguration);
    //     return new CorsWebFilter(source);
    // }
}
