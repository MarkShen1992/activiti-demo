package io.github.db.entity;

import com.google.common.collect.Maps;
import org.activiti.engine.ManagementService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 运行时
 *
 * @author shenjunyu
 * @email sjy13149@cnki.net
 * @since 2022/1/4 8:22
 */
public class DB0500RUTest {

    private static final Logger logger = LoggerFactory.getLogger(DB0500RUTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti-mysql.cfg.xml");

    @Test
    public void testRemoveTableSchema() {
        ManagementService managementService = activitiRule.getManagementService();
        managementService.executeCommand(commandContext -> {
            commandContext.getDbSqlSession().dbSchemaDrop();
            logger.info("remove table schema...");
            return null;
        });
    }

    @Test
    public void testRunTime() {
        activitiRule.getRepositoryService().createDeployment().name("second audit process...")
            .addClasspathResource("second_approve.bpmn20.xml").deploy();
        Map<String, Object> variables = Maps.newHashMap();
        variables.put("key1", "value1");
        ProcessInstance processInstance =
            activitiRule.getRuntimeService().startProcessInstanceByKey("second_approve", variables);
    }

    @Test
    public void testSetOwner() {
        TaskService taskService = activitiRule.getTaskService();
        Task task = taskService.createTaskQuery().processDefinitionKey("second_approve").singleResult();
        taskService.setOwner(task.getId(), "user1");
    }

    @Test
    public void testMsg() {
        activitiRule.getRepositoryService().createDeployment().addClasspathResource("my-process_msg.bpmn20.xml")
            .deploy();
    }

    @Test
    public void testMsg02() {
        activitiRule.getRepositoryService().createDeployment()
            .addClasspathResource("my-process_msg_received.bpmn20.xml").deploy();
        activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");
    }

    @Test
    public void testJob() throws InterruptedException {
        activitiRule.getRepositoryService().createDeployment().addClasspathResource("my-process_job.bpmn20.xml")
            .deploy();
        Thread.sleep(1000 * 30L);
    }
}
