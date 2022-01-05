package io.github.delegate;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shenjunyu
 * @email sjy13149@cnki.net
 * @since 2022/1/5 17:16
 */
public class MyJavaDelegate implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(MDCErrorDelegate.class);

    @Override
    public void execute(DelegateExecution delegateExecution) {
        LOGGER.info("run my Java Delegate = {}", this);
    }
}
