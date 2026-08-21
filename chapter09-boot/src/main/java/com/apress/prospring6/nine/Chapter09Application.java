package com.apress.prospring6.nine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

// scanBasePackages is scoped to just the reused repos/services packages from chapter09,
// deliberately leaving out com.apress.prospring6.nine.config: those classes hand-roll
// their own DataSource/EntityManagerFactory/TransactionManager beans, which would
// collide with the ones Spring Boot auto-configures from application.yaml here.
// Entity scanning is unaffected by scanBasePackages - Boot still picks up
// com.apress.prospring6.nine.entities automatically since it's a sub-package of this
// class's own package.
@SpringBootApplication(scanBasePackages = {
        "com.apress.prospring6.nine.repos",
        "com.apress.prospring6.nine.services",
        "com.apress.prospring6.nine.bootconfig"
})
public class Chapter09Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Chapter09Application.class);

    public static void main(String[] args) {
        var ctx = SpringApplication.run(Chapter09Application.class, args);

        // simple connectivity smoke test against the MariaDB container configured
        // in application.yaml - independent of whatever schema happens to be loaded
        var jdbcTemplate = ctx.getBean(JdbcTemplate.class);
        var product = jdbcTemplate.execute((java.sql.Connection con) ->
                con.getMetaData().getDatabaseProductName() + " " + con.getMetaData().getDatabaseProductVersion());
        LOGGER.info("Connected to {}", product);
    }
}
