package com.apress.prospring6.fourteen;

import com.apress.prospring6.nine.config.BasicDataSourceCfg;
import com.apress.prospring6.nine.config.TransactionCfg;
import jakarta.servlet.Filter;
import org.jspecify.annotations.Nullable;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import java.nio.charset.StandardCharsets;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?> @Nullable [] getRootConfigClasses() {
        return new Class[]{BasicDataSourceCfg.class, TransactionCfg.class};
    }

    @Override
    protected Class<?> @Nullable [] getServletConfigClasses() {
        return new Class[] {WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter @Nullable [] getServletFilters() {
        final CharacterEncodingFilter cef = new CharacterEncodingFilter();
        cef.setEncoding(StandardCharsets.UTF_8.name());
        cef.setForceEncoding(true);
        return new Filter[]{new HiddenHttpMethodFilter(), cef};
    }
}
