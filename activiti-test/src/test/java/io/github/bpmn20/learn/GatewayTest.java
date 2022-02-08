package io.github.bpmn20.learn;

import java.util.List;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.collections.Maps;

/**
 * 网关测试
 *
 * @author shenjunyu
 * @email sjy13149@cnki.net
 * @since 2022/1/5 9:00
 */
public class GatewayTest {

    private static final Logger logger = LoggerFactory.getLogger(GatewayTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @Test
    @Deployment(resources = {"bpmn2.0_gateway_exclusive_gateway_1.bpmn20.xml"})
    public void testExclusiveGateway1() {
        Map<String, Object> vars = Maps.newHashMap();
        vars.put("score", 91);
        ProcessInstance processInstance =
            activitiRule.getRuntimeService().startProcessInstanceByKey("my-process", vars);
        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
        logger.info("task.name = {}", task.getName());
    }

    @Test
    @Deployment(resources = {"bpmn2.0_gateway_exclusive_gateway_1.bpmn20.xml"})
    public void testExclusiveGateway2() {
        Map<String, Object> vars = Maps.newHashMap();
        vars.put("score", 85);
        ProcessInstance processInstance =
            activitiRule.getRuntimeService().startProcessInstanceByKey("my-process", vars);
        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
        logger.info("task.name = {}", task.getName());
    }

    @Test
    @Deployment(resources = {"bpmn2.0_gateway_exclusive_gateway_1.bpmn20.xml"})
    public void testExclusiveGateway3() {
        Map<String, Object> vars = Maps.newHashMap();
        vars.put("score", 70);
        ProcessInstance processInstance =
            activitiRule.getRuntimeService().startProcessInstanceByKey("my-process", vars);
        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
        logger.info("task.name = {}", task.getName());
    }

    @Test
    @Deployment(resources = {"bpmn2.0_gateway_parallel_gateway_1.bpmn20.xml"})
    public void testParallelGateway1() {
        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");
        List<Task> tasks =
            activitiRule.getTaskService().createTaskQuery().processInstanceId(processInstance.getId()).listPage(0, 100);
        for (Task task : tasks) {
            logger.info("task.name = {}", task.getName());
            activitiRule.getTaskService().complete(task.getId());
        }
        logger.info("task.size = {}", tasks.size());

        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
        logger.info("task.name = {}", task.getName());
    }
}