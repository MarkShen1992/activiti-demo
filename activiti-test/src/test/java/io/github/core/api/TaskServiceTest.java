package io.github.core.api;

import com.google.common.collect.Maps;
import io.github.utils.StringUtils;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.*;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNull;

/**
 * TaskServiceTest Test
 *
 * @author shenjunyu
 * @email sjy13149@cnki.net
 * @since 2021/12/31 7:58
 */
public class TaskServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(TaskServiceTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @Test
    @Deployment(resources = {"my-process_task.bpmn20.xml"})
    public void testTaskService() {
        Map<String, Object> params = Maps.newHashMap();
        params.put("msg", "my test message...");
        ProcessInstance processInstance =
            activitiRule.getRuntimeService().startProcessInstanceByKey("my-process", params);
        TaskService taskService = activitiRule.getTaskService();
        Task task = taskService.createTaskQuery().singleResult();
        logger.info("task = {}", StringUtils.toJSONString(task));
        logger.info("task description = {}", task.getDescription());

        // 设置变量
        taskService.setVariable(task.getId(), "key1", "value1");
        taskService.setVariableLocal(task.getId(), "localKey1", "localValue1");

        // 获取变量
        Map<String, Object> taskServiceVariables = taskService.getVariables(task.getId());
        Map<String, Object> taskServiceVariablesLocal = taskService.getVariablesLocal(task.getId());
        Map<String, Object> processVariables = activitiRule.getRuntimeService().getVariables(processInstance.getId());
        logger.info("taskServiceVariables = {}", taskServiceVariables);
        logger.info("taskServiceVariablesLocal = {}", taskServiceVariablesLocal);
        logger.info("processVariables = {}", processVariables);

        Map<String, Object> completeParams = Maps.newHashMap();
        completeParams.put("cKey1", "cValue1");
        // 把当前的流程成执行完成后，流转到下个流程
        taskService.complete(task.getId(), completeParams);

        Task task1 = taskService.createTaskQuery().taskId(task.getId()).singleResult();
        assertNull(task1);
    }

    @Test
    @Deployment(resources = {"my-process_task.bpmn20.xml"})
    public void testTaskServiceUser() {
        Map<String, Object> params = Maps.newHashMap();
        params.put("msg", "my test message...");
        ProcessInstance processInstance =
            activitiRule.getRuntimeService().startProcessInstanceByKey("my-process", params);
        TaskService taskService = activitiRule.getTaskService();
        Task task = taskService.createTaskQuery().singleResult();
        logger.info("task = {}", StringUtils.toJSONString(task));
        logger.info("task description = {}", task.getDescription());

        // owner 一般为流程的发起人
        taskService.setOwner(task.getId(), "user1");
        // taskService.setAssignee(task.getId(), "shenjy");
        List<Task> taskList =
            taskService.createTaskQuery().taskCandidateUser("shenjy").taskUnassigned().listPage(0, 100);
        for (Task task1 : taskList) {
            try {
                taskService.claim(task1.getId(), "shenjy");
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }

        List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(task.getId());
        for (IdentityLink identityLink : identityLinksForTask) {
            logger.info("identityLink = {}", identityLink);
        }

        List<Task> tasks = taskService.createTaskQuery().taskAssignee("shenjy").listPage(0, 100);
        for (Task task1 : tasks) {
            Map<String, Object> vars = Maps.newHashMap();
            vars.put("cKey1", "vKey1");
            taskService.complete(task1.getId(), params);
        }

        tasks = taskService.createTaskQuery().taskAssignee("shenjy").listPage(0, 100);
        logger.info("是否存在 {}", !CollectionUtils.isEmpty(tasks));
    }

    @Test
    @Deployment(resources = {"my-process_task.bpmn20.xml"})
    public void testTaskAttachment() {
        Map<String, Object> params = Maps.newHashMap();
        params.put("msg", "my test message...");
        ProcessInstance processInstance =
            activitiRule.getRuntimeService().startProcessInstanceByKey("my-process", params);
        TaskService taskService = activitiRule.getTaskService();
        Task task = taskService.createTaskQuery().singleResult();
        taskService.createAttachment("url", task.getId(), task.getProcessInstanceId(), "name", "desc", "/url/test.png");
        List<Attachment> taskAttachments = taskService.getTaskAttachments(task.getId());
        for (Attachment taskAttachment : taskAttachments) {
            logger.info("taskAttachment = {}", StringUtils.toJSONString(taskAttachment));
        }
    }

    @Test
    @Deployment(resources = {"my-process_task.bpmn20.xml"})
    public void testTaskComment() {
        Map<String, Object> params = Maps.newHashMap();
        params.put("msg", "my test message...");
        ProcessInstance processInstance =
            activitiRule.getRuntimeService().startProcessInstanceByKey("my-process", params);
        TaskService taskService = activitiRule.getTaskService();
        Task task = taskService.createTaskQuery().singleResult();
        taskService.setOwner(task.getId(), "user1");
        taskService.setAssignee(task.getId(), "shenjy");

        taskService.addComment(task.getId(), task.getProcessInstanceId(), "测试...");
        taskService.addComment(task.getId(), task.getProcessInstanceId(), "测试2...");

        List<Comment> taskComments = taskService.getTaskComments(task.getId());
        for (Comment taskComment : taskComments) {
            logger.info("task comment = {}", StringUtils.toJSONString(taskComment));
        }

        // 会记录所有变化
        List<Event> taskEvents = taskService.getTaskEvents(task.getId());
        for (Event taskEvent : taskEvents) {
            logger.info("task event = {}", StringUtils.toJSONString(taskEvent));
        }
    }
}
