package studio.tri.cargo_server.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import studio.tri.cargo_server.realm.DefaultRealm;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    public DefaultRealm defaultRealm() {
        return new DefaultRealm();
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(defaultRealm());
        return manager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager());
        bean.setLoginUrl("/login");
        bean.setSuccessUrl("/");
        //bean.setUnauthorizedUrl("/unauth");
        Map<String,String> map = new HashMap<>();
        map.put("/login","anon");
        map.put("/register","anon");

        map.put("/api/server_status","anon");
        map.put("/api/editprofile","authc");
        map.put("/api/delivery/get","authc");
        map.put("/api/delivery/update","authc");
        map.put("/api/package/add","authc");
        map.put("/api/package/getlist","authc");

        map.put("/logout","anon");

        bean.setFilterChainDefinitionMap(map);

        return bean;
    }

}
