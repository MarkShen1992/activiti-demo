package io.github.delegate;

import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author shenjunyu
 * @email sjy13149@cnki.net
 * @since 2022/1/5 17:16
 */
public class MyPayDelegate implements JavaDelegate, Serializable {

    private static final long serialVersionUID = -6289202413107930123L;

    private static final Logger LOGGER = LoggerFactory.getLogger(MDCErrorDelegate.class);

    @Override
    public void execute(DelegateExecution delegateExecution) {
        LOGGER.info("variables = {}", delegateExecution.getVariables());
        LOGGER.info("确认支付 = {}", this);
        delegateExecution.getParent().setVariableLocal("key2", "value2");
        delegateExecution.getParent().setVariable("key1", "value1");
        delegateExecution.getParent().setVariable("key3", "value3");
        Object errorFlag = delegateExecution.getVariable("errorFlag");
        if (errorFlag instanceof Boolean && Objects.equals(true, errorFlag)) {
            throw new BpmnError("bpmnError");
        }
    }
}
