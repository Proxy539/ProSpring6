package com.apress.prospring6.three;

public interface NewsLetterSender {

    void setSmtpServer(String smtpServer);

    String getSmtpServer();

    void setFromAddress(String fromAddress);

    String getFromAddress();

    void send();

}
