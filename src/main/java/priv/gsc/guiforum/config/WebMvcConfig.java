package priv.gsc.guiforum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import priv.gsc.guiforum.interceptor.LoginRequiredInterceptor;
import priv.gsc.guiforum.interceptor.LoginTicketInterceptor;

/**
 * 针对拦截器的配置类
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginTicketInterceptor loginTicketInterceptor;

    @Autowired
    private LoginRequiredInterceptor loginRequiredInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginTicketInterceptor)
                .excludePathPatterns("/**/*.css","/**/*.scss","/**/*.js","/**/*.png","/**/*.jpg","/**/*.jpeg","/**/*.ico");

        registry.addInterceptor(loginRequiredInterceptor)
                .excludePathPatterns("/**/*.css","/**/*.scss","/**/*.js","/**/*.png","/**/*.jpg","/**/*.jpeg","/**/*.ico");
    }

}
