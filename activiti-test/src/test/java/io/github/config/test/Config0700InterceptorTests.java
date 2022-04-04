package io.github.config.test;

import org.activiti.engine.event.EventLogEntry;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Config0700InterceptorTests {

    private static final Logger logger = LoggerFactory.getLogger(Config0700InterceptorTests.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti_interceptor.cfg.xml");

    /**
     * 根据资源文件去创建
     */
    @Test
    @Deployment(resources = {"my-process.bpmn20.xml"})
    public void testConfigEventLog() {
        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");
        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
        activitiRule.getTaskService().complete(task.getId());

        List<EventLogEntry> eventLogEntries =
            activitiRule.getManagementService().getEventLogEntriesByProcessInstanceId(processInstance.getId());
        for (EventLogEntry eventLogEntry : eventLogEntries) {
            logger.info("event.type = {}, event.data = {}", eventLogEntry.getType(),
                new String(eventLogEntry.getData()));
        }
        logger.info("eventLogEntries size {}", eventLogEntries.size());
    }
}