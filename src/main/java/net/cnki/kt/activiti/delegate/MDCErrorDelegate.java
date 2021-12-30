package net.cnki.kt.activiti.delegate;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shenjunyu
 * @email sjy13149@cnki.net
 * @since 2021/12/30 14:02
 */
public class MDCErrorDelegate implements JavaDelegate {

    private static final Logger logger = LoggerFactory.getLogger(MDCErrorDelegate.class);

    @Override
    public void execute(DelegateExecution delegateExecution) {
        logger.info("run MDCErrorDelegate");
        throw new RuntimeException("only test");
    }
}
