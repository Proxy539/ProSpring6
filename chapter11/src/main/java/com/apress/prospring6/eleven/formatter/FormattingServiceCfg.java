package com.apress.prospring6.eleven.formatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@Configuration
public class FormattingServiceCfg {

    @Bean
    public FormattingConversionService conversionService() {
        var formattingConversionServiceBean = new DefaultFormattingConversionService(true);
        formattingConversionServiceBean.addFormatter(localDateFormatter());
        return formattingConversionServiceBean;
    }

    protected Formatter<LocalDate> localDateFormatter() {
        return new Formatter<LocalDate>() {
            @Override
            public LocalDate parse(String text, Locale locale) throws ParseException {
                return LocalDate.parse(text, getDateTimeFormatter());
            }

            @Override
            public String print(LocalDate object, Locale locale) {
                return object.format(getDateTimeFormatter());
            }

            protected DateTimeFormatter getDateTimeFormatter() {
                return DateTimeFormatter.ofPattern("yyyy-MM-dd");
            }
        };
    }
}
