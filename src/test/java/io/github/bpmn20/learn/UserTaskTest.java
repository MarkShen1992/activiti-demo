package io.github.bpmn20.learn;

import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User Task Test
 *
 * @author shenjunyu
 * @email sjy13149@cnki.net
 * @since 2022/1/5 8:31
 */
public class UserTaskTest {

    private static final Logger logger = LoggerFactory.getLogger(BPMN20Test.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @Test
    @Deployment(resources = {"bpmn2.0_usertask.bpmn20.xml"})
    public void testUserTask() {
        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");
        TaskService taskService = activitiRule.getTaskService();
        Task task = taskService.createTaskQuery().taskCandidateUser("user1").singleResult();
        logger.info("find by user1 task = {}", task);
        Task task2 = taskService.createTaskQuery().taskCandidateUser("user2").singleResult();
        logger.info("find by user2 task = {}", task2);
        Task group1 = taskService.createTaskQuery().taskCandidateGroup("group1").singleResult();
        logger.info("find by group1 task = {}", group1);

        // claim 的方式会做校验
        taskService.claim(task.getId(), "user2");
        logger.info("claim task.id = {} by user2", task.getId());
        // 不推荐使用
        // taskService.setAssignee(task.getId(), "user2");
        task = taskService.createTaskQuery().taskCandidateOrAssigned("user1").singleResult();
        logger.info("find by user1 task = {}", task);
        task = taskService.createTaskQuery().taskCandidateOrAssigned("user2").singleResult();
        logger.info("find by user2 task = {}", task);
    }

    @Test
    @Deployment(resources = {"bpmn2.0_usertask2.bpmn20.xml"})
    public void testUserTask2() {
        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");
        TaskService taskService = activitiRule.getTaskService();
        Task task = taskService.createTaskQuery().taskCandidateUser("user1").singleResult();
        logger.info("find by user1 task = {}", task);
        taskService.complete(task.getId());
    }
}