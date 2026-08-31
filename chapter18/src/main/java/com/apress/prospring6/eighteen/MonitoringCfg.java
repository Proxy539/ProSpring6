package com.apress.prospring6.eighteen;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.support.RegistrationPolicy;

@EnableMBeanExport(registration = RegistrationPolicy.REPLACE_EXISTING)
@Configuration
public class MonitoringCfg {

}
