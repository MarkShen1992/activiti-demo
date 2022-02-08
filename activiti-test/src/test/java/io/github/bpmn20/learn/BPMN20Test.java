package io.github.bpmn20.learn;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * BPMN20 标准学习
 *
 * @author shenjunyu
 * @email sjy13149@cnki.net
 * @since 2022/1/4 14:47
 */
public class BPMN20Test {

    private static final Logger logger = LoggerFactory.getLogger(BPMN20Test.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("bpmn20-activiti.cfg.xml");

    /**
     * 需要打开异步执行器，树形日志
     *
     * @throws InterruptedException
     */
    @Test
    @Deployment(resources = {"bpmn2.0_boundary_task.bpmn20.xml"})
    public void testTimerEvent01() throws InterruptedException {
        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");
        List<Task> taskList = activitiRule.getTaskService().createTaskQuery().listPage(0, 100);
        for (Task task : taskList) {
            logger.info("task.name = {}", task.getName());
        }
        logger.info("taskList.size = {}", taskList.size());
        Thread.sleep(1000 * 15L);
        taskList = activitiRule.getTaskService().createTaskQuery().listPage(0, 100);
        for (Task task : taskList) {
            logger.info("task.name = {}", task.getName());
        }
        logger.info("taskList.size = {}", taskList.size());
    }
}