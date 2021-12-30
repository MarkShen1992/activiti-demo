package net.cnki.kt.activiti.event;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Job event
 *
 * @author shenjunyu
 * @email sjy13149@cnki.net
 * @since 2021/12/30 16:29
 */
public class JobEventListener implements ActivitiEventListener {

    private static final Logger logger = LoggerFactory.getLogger(JobEventListener.class);

    @Override
    public void onEvent(ActivitiEvent activitiEvent) {
        ActivitiEventType eventType = activitiEvent.getType();
        String name = eventType.name();

        if (name.startsWith("TIMER") || name.startsWith("JOB")) {
            logger.info("监听到JOB事件 {} \t {} ", eventType, activitiEvent.getExecutionId());
        }
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }
}
