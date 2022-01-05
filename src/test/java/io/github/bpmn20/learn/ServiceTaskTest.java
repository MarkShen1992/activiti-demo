package io.github.bpmn20.learn;

import java.util.List;

import org.activiti.engine.ActivitiEngineAgenda;
import org.activiti.engine.ManagementService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shenjunyu
 * @email sjy13149@cnki.net
 * @since 2022/1/5 17:24
 */
public class ServiceTaskTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceTaskTest.class);

    @Rule
    public ActivitiRule activityRule = new ActivitiRule();

    /**
     * 直接执行
     */
    @Test
    @Deployment(resources = {"bpmn2.0_service_task_1.bpmn20.xml"})
    public void testUserTask() {
        ProcessInstance processInstance = activityRule.getRuntimeService().startProcessInstanceByKey("my-process");
        List<HistoricActivityInstance> historicActivityInstances = activityRule.getHistoryService()
            .createHistoricActivityInstanceQuery().orderByHistoricActivityInstanceEndTime().asc().list();
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            LOGGER.info("historicActivityInstance = {}", historicActivityInstance);
        }
        LOGGER.info("historicActivityInstances size = {}", historicActivityInstances.size());
    }

    @Test
    @Deployment(resources = {"bpmn2.0_service_task_2.bpmn20.xml"})
    public void testUserTask2() {
        ProcessInstance processInstance = activityRule.getRuntimeService().startProcessInstanceByKey("my-process");
        List<HistoricActivityInstance> historicActivityInstances = activityRule.getHistoryService()
            .createHistoricActivityInstanceQuery().orderByHistoricActivityInstanceEndTime().asc().list();
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            LOGGER.info("historicActivityInstance = {}", historicActivityInstance);
        }
        LOGGER.info("historicActivityInstances size = {}", historicActivityInstances.size());
    }

    /**
     * 流程等待
     */
    @Test
    @Deployment(resources = {"bpmn2.0_service_task_2.bpmn20.xml"})
    public void testUserTask3() {
        ProcessInstance processInstance = activityRule.getRuntimeService().startProcessInstanceByKey("my-process");
        List<HistoricActivityInstance> historicActivityInstances = activityRule.getHistoryService()
            .createHistoricActivityInstanceQuery().orderByHistoricActivityInstanceEndTime().asc().list();
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            LOGGER.info("historicActivityInstance = {}", historicActivityInstance);
        }
        LOGGER.info("historicActivityInstances size = {}", historicActivityInstances.size());

        Execution execution =
            activityRule.getRuntimeService().createExecutionQuery().activityId("someTask").singleResult();
        LOGGER.info("execution = {}", execution);

        ManagementService managementService = activityRule.getManagementService();
        managementService.executeCommand(commandContext -> {
            ActivitiEngineAgenda agenda = commandContext.getAgenda();
            agenda.planTakeOutgoingSequenceFlowsOperation((ExecutionEntity)execution, false);
            return null;
        });

        historicActivityInstances = activityRule.getHistoryService().createHistoricActivityInstanceQuery()
            .orderByHistoricActivityInstanceEndTime().asc().list();
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            LOGGER.info("activity = {}", historicActivityInstance);
        }
        LOGGER.info("activities size = {}", historicActivityInstances.size());
    }
}
