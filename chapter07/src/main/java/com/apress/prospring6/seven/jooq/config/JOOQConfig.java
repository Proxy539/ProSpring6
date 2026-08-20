package com.apress.prospring6.seven.jooq.config;

import org.jooq.DSLContext;
import org.jooq.conf.RenderNameCase;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class JOOQConfig {

    private static Logger LOGGER = LoggerFactory.getLogger(JOOQConfig.class);

    @Autowired
    DataSource dataSource;

    @Bean
    DSLContext dslContext() {
        try {
            return DSL.using(dataSource.getConnection(),
                    new Settings()
                            .withRenderNameCase(RenderNameCase.UPPER)
                            .withRenderQuotedNames(RenderQuotedNames.NEVER)
                            .withRenderSchema(false)
                            .withRenderGroupConcatMaxLenSessionVariable(false));
        } catch (SQLException ex) {
            LOGGER.error("Problem initializing jOOQ.DSLContext!", ex);
        }

        return null;
    }
}
