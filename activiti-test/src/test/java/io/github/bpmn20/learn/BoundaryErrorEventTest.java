package io.github.bpmn20.learn;

import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;

import com.google.common.collect.Maps;

/**
 * 边界错误事件
 *
 * @author shenjunyu
 * @email sjy13149@cnki.net
 * @since 2022/1/4 14:47
 */
public class BoundaryErrorEventTest extends PluggableActivitiTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Normally the UI will do this automatically for us.
        Authentication.setAuthenticatedUserId("kermit");
    }

    @Override
    protected void tearDown() throws Exception {
        Authentication.setAuthenticatedUserId(null);
        super.tearDown();
    }

    /**
     * 需要打开异步执行器，树形日志
     *
     * @throws InterruptedException
     */
    @Deployment(resources = {"bpmn2.0_review_sales_lead.bpmn20.xml"})
    public void testReviewSalesLeadProgress() {
        // After starting the process, a task should be assigned to the
        // initiator (normally set by GUI)
        Map<String, Object> variables = Maps.newHashMap();
        variables.put("details", "very interesting");
        variables.put("customerName", "Alfresco");
        String procId = runtimeService.startProcessInstanceByKey("reviewSaledLead", variables).getId();
        Task task = taskService.createTaskQuery().taskAssignee("kermit").singleResult();
        assertEquals("Provider new sales lead", task.getName());

        // After completing the task, the review subprocess will be active
        taskService.complete(task.getId());
        Task task1 = taskService.createTaskQuery().taskCandidateGroup("accountancy").singleResult();
        assertEquals("Review customer rating", task1.getName());
        Task task2 = taskService.createTaskQuery().taskCandidateGroup("management").singleResult();
        assertEquals("Review Profitability", task2.getName());

        // Complete the management task by starting that not enough info was provided
        // This should throw the error event, which closes the subprocess
        variables = Maps.newHashMap();
        variables.put("notEnoughInformation", true);
        taskService.complete(task2.getId(), variables);

        // the provide additional details task should now be active
        Task providedDetailsTask = taskService.createTaskQuery().taskAssignee("kermit").singleResult();
        assertEquals("Provide additional details", providedDetailsTask.getName());

        // providing more details(completing the task), will activate the subprocess again.
        taskService.complete(providedDetailsTask.getId());
        List<Task> reviewTasks = taskService.createTaskQuery().orderByTaskName().asc().list();
        assertEquals("Review customer rating", reviewTasks.get(1).getName());
        assertEquals("Review Profitability", reviewTasks.get(0).getName());

        // completing both tasks normally ends the process
        taskService.complete(reviewTasks.get(1).getId());
        variables.put("notEnoughInformation", false);
        taskService.complete(reviewTasks.get(0).getId(), variables);
        assertProcessEnded(procId);
    }
}