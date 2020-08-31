package cn.xanderye.tbautosign.config;

import cn.xanderye.tbautosign.filter.CorsFilter;
import cn.xanderye.tbautosign.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by Xander on 2018-11-05.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Value("${static.path.pattern}")
    private String staticPathPattern;
    @Value("${static.resources.locations}")
    private String staticResourcesLocations;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.loginInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/upload/**")
                .excludePathPatterns("/static/**")
                .excludePathPatterns("/sign/**")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/register")
                .excludePathPatterns("/captcha")
                .excludePathPatterns("/user/check")
                .excludePathPatterns("/tool/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(staticPathPattern.split(",")).addResourceLocations(staticResourcesLocations.split(","));
    }

    /**
     * 过滤器
     * @param
     * @return org.springframework.boot.web.servlet.FilterRegistrationBean
     * @author XanderYe
     * @date 2019/8/27
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new CorsFilter());
        bean.addUrlPatterns("/*");
        return bean;
    }
}