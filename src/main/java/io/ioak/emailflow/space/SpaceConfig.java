package io.ioak.emailflow.space;

import com.google.common.collect.Lists;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.target.ThreadLocalTargetSource;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.*;

import javax.servlet.Filter;

@Configuration
public class SpaceConfig {

    @Bean
    public Filter tenancyFilter() {
        return new SpaceFilter();
    }

    @Bean
    public FilterRegistrationBean spaceFilterRegistration() {
        FilterRegistrationBean result = new FilterRegistrationBean();
        result.setFilter(tenancyFilter());
        result.setUrlPatterns(Lists.newArrayList("/*"));
        result.setName("Space Filter");
        result.setOrder(1);
        return result;
    }

    @Bean(destroyMethod = "destroy")
    public ThreadLocalTargetSource threadLocalTenantStore() {
        ThreadLocalTargetSource result = new ThreadLocalTargetSource();
        result.setTargetBeanName("spaceHolder");
        return result;
    }

    @Primary
    @Bean
    public ProxyFactoryBean proxiedThreadLocalTargetSource(ThreadLocalTargetSource threadLocalTargetSource) {
        ProxyFactoryBean result = new ProxyFactoryBean();
        result.setTargetSource(threadLocalTargetSource);
        return result;
    }

    @Bean
    @Scope(scopeName = "prototype")
    public SpaceHolder spaceHolder() {
        return new SpaceHolder();
    }

}
