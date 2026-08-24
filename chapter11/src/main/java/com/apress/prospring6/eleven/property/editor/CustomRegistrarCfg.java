package com.apress.prospring6.eleven.property.editor;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class CustomRegistrarCfg {

    @Bean
    public PropertyEditorRegistrar registrar() {
        return registry -> registry.registerCustomEditor(LocalDate.class, new LocalDatePropertyEditor());
    }

    @Bean
    public CustomEditorConfigurer customEditorConfigurer() {
        var cus = new CustomEditorConfigurer();
        var registrars = new PropertyEditorRegistrar[1];
        registrars[0] = registrar();
        cus.setPropertyEditorRegistrars(registrars);
        return cus;
    }
}
