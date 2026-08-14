package com.apress.prospring6.four;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class AppProperty {
    private String applicationHome;
    private String userHome;

    public String getApplicationHome() {
        return applicationHome;
    }

    @Autowired
    public void setApplicationHome(@Value("${application.home}") String applicationHome) {
        this.applicationHome = applicationHome;
    }

    public String getUserHome() {
        return userHome;
    }

    @Autowired
    public void setUserHome(@Value("${user.home}") String userHome) {
        this.userHome = userHome;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("applicationHome", applicationHome)
                .append("userHome", userHome)
                .toString();
    }
}
