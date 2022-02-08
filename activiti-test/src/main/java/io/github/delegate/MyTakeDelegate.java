package io.github.delegate;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * 确认收货
 *
 * @author shenjunyu
 * @email sjy13149@cnki.net
 * @since 2022/1/5 17:16
 */
public class MyTakeDelegate implements JavaDelegate, Serializable {

    private static final long serialVersionUID = -6289202413107930123L;

    private static final Logger LOGGER = LoggerFactory.getLogger(MDCErrorDelegate.class);

    @Override
    public void execute(DelegateExecution delegateExecution) {
        LOGGER.info("确认收货 = {}", this);
    }
}
