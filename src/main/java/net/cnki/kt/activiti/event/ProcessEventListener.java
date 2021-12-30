package net.cnki.kt.activiti.event;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 流程 event
 *
 * @author shenjunyu
 * @email sjy13149@cnki.net
 * @since 2021/12/30 16:29
 */
public class ProcessEventListener implements ActivitiEventListener {

    private static final Logger logger = LoggerFactory.getLogger(ProcessEventListener.class);

    @Override
    public void onEvent(ActivitiEvent activitiEvent) {
        ActivitiEventType type = activitiEvent.getType();
        if (ActivitiEventType.PROCESS_STARTED.equals(type)) {
            logger.info("流程启动 {} \t {}", activitiEvent, activitiEvent.getExecutionId());
        } else if (ActivitiEventType.PROCESS_COMPLETED.equals(type)) {
            logger.info("流程完成 {} \t {}", activitiEvent, activitiEvent.getExecutionId());
        }
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }
}
