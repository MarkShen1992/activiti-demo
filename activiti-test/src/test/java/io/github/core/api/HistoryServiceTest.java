package io.github.core.api;

import com.google.common.collect.Maps;
import io.github.utils.StringUtils;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceBuilder;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNull;

/**
 * HistoryServiceTest
 *
 * @author shenjunyu
 * @email sjy13149@cnki.net
 * @since 2021/12/31 14:24
 */
public class HistoryServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(HistoryServiceTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti_history.cfg.xml");

    @Test
    @Deployment(resources = {"my-process.bpmn20.xml"})
    public void testHistoryService() {
        HistoryService historyService = activitiRule.getHistoryService();
        ProcessInstanceBuilder processInstanceBuilder = activitiRule.getRuntimeService().createProcessInstanceBuilder();
        Map<String, Object> vars = Maps.newHashMap();
        vars.put("key0", "value0");
        vars.put("key1", "value1");
        vars.put("key2", "value2");

        Map<String, Object> transientVars = Maps.newHashMap();
        transientVars.put("tKey1", "tValue1");
        ProcessInstance processInstance = processInstanceBuilder.processDefinitionKey("my-process")
                .variables(vars)
                .transientVariables(transientVars)
                .start();

        activitiRule.getRuntimeService().setVariable(processInstance.getId(), "key1", "value1_1");

        Task task = activitiRule.getTaskService().createTaskQuery()
                .processInstanceId(processInstance.getId()).singleResult();

        // activitiRule.getTaskService().complete(task.getId(), vars);
        Map<String, String> params = Maps.newHashMap();
        params.put("fKey1", "fValue1");
        params.put("key2", "value_2_2");
        activitiRule.getFormService().submitTaskFormData(task.getId(), params);

        List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery()
                .listPage(0, 100);
        for (HistoricProcessInstance historicProcessInstance : historicProcessInstances) {
            logger.info("historicProcessInstance = {}", StringUtils.toJSONString(historicProcessInstance));
        }

        List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery()
                .listPage(0, 100);
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            logger.info("historicActivityInstance = {}", StringUtils.toJSONString(historicActivityInstance));
        }

        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
                .listPage(0, 100);
        for (HistoricTaskInstance historicTaskInstance : historicTaskInstances) {
            logger.info("historicTaskInstance = {}", StringUtils.toJSONString(historicTaskInstance));
        }

        List<HistoricVariableInstance> historicVariableInstances = historyService.createHistoricVariableInstanceQuery()
                .listPage(0, 100);
        for (HistoricVariableInstance historicVariableInstance : historicVariableInstances) {
            logger.info("historicVariableInstance = {}", StringUtils.toJSONString(historicVariableInstance));
        }

        List<HistoricDetail> historicDetails = historyService.createHistoricDetailQuery().listPage(0, 100);
        for (HistoricDetail historicDetail : historicDetails) {
            logger.info("historicDetail = {}", StringUtils.toJSONString(historicDetail));
        }

        ProcessInstanceHistoryLog processInstanceHistoryLog = historyService.createProcessInstanceHistoryLogQuery(processInstance.getId())
                .includeVariables()
                .includeFormProperties()
                .includeComments()
                .includeTasks()
                .includeActivities()
                .includeVariableUpdates().singleResult();

        List<HistoricData> historicDataList = processInstanceHistoryLog.getHistoricData();
        for (HistoricData historicData : historicDataList) {
            logger.info("historicData = {}", StringUtils.toJSONString(historicData));
        }

        historyService.deleteHistoricProcessInstance(processInstance.getId());
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstance.getId()).singleResult();
        assertNull(historicProcessInstance);
    }
}