package net.cnki.kt.activiti.event;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义 event
 *
 * @author shenjunyu
 * @email sjy13149@cnki.net
 * @since 2021/12/30 16:29
 */
public class CustomizeEventListener implements ActivitiEventListener {

    private static final Logger logger = LoggerFactory.getLogger(CustomizeEventListener.class);

    @Override
    public void onEvent(ActivitiEvent activitiEvent) {
        ActivitiEventType type = activitiEvent.getType();
        if (ActivitiEventType.CUSTOM.equals(type)) {
            logger.info("自定义事件 {} \t {}", activitiEvent, activitiEvent.getExecutionId());
        }
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }
}
