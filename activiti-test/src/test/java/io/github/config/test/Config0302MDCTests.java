package io.github.config.test;

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

public class Config0302MDCTests {

    private static final Logger logger = LoggerFactory.getLogger(Config0302MDCTests.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti_mdc.cfg.xml");

    /**
     * 根据资源文件去创建
     */
    @Test
    @Deployment(resources = {"my-process.bpmn20.xml"})
    public void testConfigMDC1() {
        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");
        assertNotNull(processInstance);

        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
        assertEquals("Activiti is awesome!", task.getName());
        activitiRule.getTaskService().complete(task.getId());
    }
}