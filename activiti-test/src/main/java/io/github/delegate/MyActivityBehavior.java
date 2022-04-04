package io.github.delegate;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.delegate.ActivityBehavior;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shenjunyu
 * @email sjy13149@cnki.net
 * @since 2022/1/5 17:18
 */
public class MyActivityBehavior implements ActivityBehavior {

    private static final long serialVersionUID = 7589192374097452496L;

    private static final Logger LOGGER = LoggerFactory.getLogger(MDCErrorDelegate.class);

    @Override
    public void execute(DelegateExecution delegateExecution) {
        LOGGER.info("run my activity behavior.");
    }
}
