package io.github.delegate;

import java.io.Serializable;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shenjunyu
 * @email sjy13149@cnki.net
 * @since 2022/1/5 17:16
 */
public class MyJavaDelegate implements JavaDelegate, Serializable {

    private static final long serialVersionUID = -6289202413107930123L;

    private static final Logger LOGGER = LoggerFactory.getLogger(MDCErrorDelegate.class);

    private Expression name;
    private Expression desc;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        if (name != null) {
            Object value = name.getValue(delegateExecution);
            LOGGER.info("name = {}", value);
        }
        if (desc != null) {
            Object value = desc.getValue(delegateExecution);
            LOGGER.info("desc = {}", value);
        }
        LOGGER.info("run my Java Delegate = {}", this);
    }
}
