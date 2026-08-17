package com.apress.prospring6.five;

import com.apress.prospring6.five.introduction.Contact;
import com.apress.prospring6.five.introduction.IsModified;
import com.apress.prospring6.five.introduction.IsModifiedAdvisor;
import com.apress.prospring6.five.introduction.IsModifiedMixin;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IntroductionAopConfig {

    @Bean
    public Contact guitarist() {
        var contact = new Contact();
        contact.setName("John Mayer");
        return contact;
    }

    @Bean
    public IsModifiedAdvisor advisor() {
        return new IsModifiedAdvisor();
    }

    @Bean
    public Contact proxy() {
        ProxyFactoryBean pfb = new ProxyFactoryBean();
        pfb.setProxyTargetClass(true);
        pfb.setTarget(guitarist());
        pfb.addAdvisor(advisor());
        pfb.setFrozen(true);
        return (Contact) pfb.getObject();
    }
}
