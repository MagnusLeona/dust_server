package per.magnus.dust.components.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import per.magnus.dust.components.web.interceptor.LoginCheckInterceptor;
import per.magnus.dust.components.web.resolver.DustUserArgumentResolver;

import javax.annotation.Resource;
import java.util.List;

@Configuration
public class DustWebConfiguration extends WebMvcConfigurationSupport {

    @Resource
    DustUserArgumentResolver dustUserArgumentResolver;
    @Resource
    LoginCheckInterceptor authCheckInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        // 添加拦截器
        registry.addInterceptor(authCheckInterceptor).addPathPatterns("/**").excludePathPatterns("/static/");
        super.addInterceptors(registry);
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(dustUserArgumentResolver);
        super.addArgumentResolvers(argumentResolvers);
    }
}
