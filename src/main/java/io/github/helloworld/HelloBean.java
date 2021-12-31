package io.github.helloworld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试
 *
 * @author shenjunyu
 * @email sjy13149@cnki.net
 * @since 2021/12/28 10:53
 */
public class HelloBean {
    private static final Logger logger = LoggerFactory.getLogger(HelloBean.class);

    public void sayHello() {
        logger.info("hello...");
    }

}
