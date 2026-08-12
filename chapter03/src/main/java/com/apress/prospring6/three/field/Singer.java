package com.apress.prospring6.three.field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("singer")
public class Singer {

    @Autowired
    private Inspiration inspirationBean;

    public void sign() {
        System.out.println("..." + inspirationBean.getLyric());
    }

}
