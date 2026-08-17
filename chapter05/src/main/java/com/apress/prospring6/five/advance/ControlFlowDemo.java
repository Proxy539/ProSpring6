package com.apress.prospring6.five.advance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.ControlFlowPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;

public class ControlFlowDemo {

    private static Logger LOGGER = LoggerFactory.getLogger(ControlFlowDemo.class);

    public static void main(String[] args) {
        ControlFlowDemo ex = new ControlFlowDemo();
        ex.run();
    }

    public void run() {
        TestBean target = new TestBean();
        Pointcut pc = new ControlFlowPointcut(ControlFlowDemo.class, "test");
        Advisor advisor = new DefaultPointcutAdvisor(pc, new SimpleBeforeAdvance());

        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(target);
        pf.addAdvisor(advisor);


        TestBean proxy = (TestBean) pf.getProxy();
        LOGGER.info("\tTrying normal invoke");
        proxy.foo();
        LOGGER.info("\tTrying under ControlFlowDemo.test()");
        test(proxy);
    }

    private void test(TestBean bean) {
        bean.foo();
    }
}
