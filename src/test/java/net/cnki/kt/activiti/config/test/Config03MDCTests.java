package net.cnki.kt.activiti.config.test;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class Config03MDCTests {

    private static final Logger logger = LoggerFactory.getLogger(Config03MDCTests.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    /**
     * 根据资源文件去创建
     */
    @Test
    @Deployment(resources = {"my-process.bpmn20.xml"})
    public void testConfig1() {
        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");
        assertNotNull(processInstance);

        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
        assertEquals("Activiti is awesome!", task.getName());
        activitiRule.getTaskService().complete(task.getId());
    }
}