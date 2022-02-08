package io.github.core.api;

import com.google.common.collect.Maps;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceBuilder;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

/**
 * RunTimeServiceTest Test
 *
 * @author shenjunyu
 * @email sjy13149@cnki.net
 * @since 2021/12/31 7:58
 */
public class RunTimeServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(RunTimeServiceTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @Test
    @Deployment(resources = {"my-process.bpmn20.xml"})
    public void testRuntimeServiceStartProcessByKey() {
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        Map<String, Object> params = Maps.newHashMap();
        params.put("key1", "value1");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("my-process", params);
        logger.info("process instance {}", processInstance);
    }

    @Test
    @Deployment(resources = {"my-process.bpmn20.xml"})
    public void testRuntimeServiceStartProcessById() {
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        RepositoryService repositoryService = activitiRule.getRepositoryService();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().singleResult();
        Map<String, Object> params = Maps.newHashMap();
        params.put("key1", "value1");
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), params);
        logger.info("process instance {}", processInstance);
    }

    @Test
    @Deployment(resources = {"my-process.bpmn20.xml"})
    public void testProcessInstanceBuilder() {
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        Map<String, Object> params = Maps.newHashMap();
        params.put("key1", "value1");
        ProcessInstanceBuilder processInstanceBuilder = runtimeService.createProcessInstanceBuilder();
        processInstanceBuilder.businessKey("businessKey001")
                .processDefinitionKey("my-process")
                .variables(params)
                .start();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("my-process", params);
        logger.info("process instance {}", processInstance);
    }

    /**
     * 流程过程中，查找和修改变量
     */
    @Test
    @Deployment(resources = {"my-process.bpmn20.xml"})
    public void testVariables() {
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        Map<String, Object> params = Maps.newHashMap();
        params.put("key1", "value1");
        params.put("key2", "value2");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("my-process", params);
        logger.info("process instance {}", processInstance);
        // 添加
        runtimeService.setVariable(processInstance.getId(), "key3", "value3");
        // 修改
        runtimeService.setVariable(processInstance.getId(), "key2", "value2_1");
        // 查询
        Map<String, Object> variables = runtimeService.getVariables(processInstance.getId());
        logger.info("variables = [{}]", variables);
    }

    /**
     * 查询流程实例
     */
    @Test
    @Deployment(resources = {"my-process.bpmn20.xml"})
    public void testProcessInstanceQuery() {
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        Map<String, Object> params = Maps.newHashMap();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("my-process", params);
        logger.info("process instance {}", processInstance);

        ProcessInstance processInstance1 = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstance.getId()).singleResult();
        assertNotNull(processInstance1);
    }

    /**
     * 查询流程实例
     */
    @Test
    @Deployment(resources = {"my-process.bpmn20.xml"})
    public void testExecutionQuery() {
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        Map<String, Object> params = Maps.newHashMap();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("my-process", params);
        logger.info("process instance {}", processInstance);

        List<Execution> executions = runtimeService.createExecutionQuery().listPage(0, 100);
        for (Execution execution : executions) {
            logger.info("execution = {}", execution);
        }
    }

    @Test
    @Deployment(resources = {"my-process_trigger.bpmn20.xml"})
    public void testTrigger() {
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("my-process");

        Execution someTask = runtimeService.createExecutionQuery().activityId("someTask").singleResult();
        logger.info("someTask = {}", someTask);
        runtimeService.trigger(someTask.getId());

        someTask = runtimeService.createExecutionQuery()
                .activityId("someTask")
                .singleResult();
        logger.info("someTask = {}", someTask);
    }

    @Test
    @Deployment(resources = {"my-process_signal_received.bpmn20.xml"})
    public void testSignalEventReceived() {
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("my-process");
        Execution execution = runtimeService.createExecutionQuery()
                .signalEventSubscriptionName("my-signal").singleResult();
        logger.info("execution = {}", execution);
        runtimeService.signalEventReceived("my-signal");
    }

    @Test
    @Deployment(resources = {"my-process_msg_received.bpmn20.xml"})
    public void testMessageEventReceived() {
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("my-process");
        assertNotNull(processInstance);
        Execution execution = runtimeService.createExecutionQuery()
                .messageEventSubscriptionName("my-message").singleResult();
        logger.info("execution = {}", execution);
        runtimeService.messageEventReceived("my-message", execution.getId());
    }

    @Test
    @Deployment(resources = {"my-process_msg.bpmn20.xml"})
    public void testMessageStart() {
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByMessage("my-message");
        logger.info("processInstance = {}", processInstance);
    }
}
