package io.github.config.test;

import com.google.common.collect.Maps;
import io.github.utils.StringUtils;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class Config0400HistoryLevelTests {

    private static final Logger logger = LoggerFactory.getLogger(Config0400HistoryLevelTests.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti_history.cfg.xml");

    /**
     * History Level: NONE, AUDIT(Default), ACTIVITY, FULL
     *
     * @see HistoryLevel
     */
    @Test
    @Deployment(resources = {"my-process.bpmn20.xml"})
    public void testConfigHistoryLevel() {
        // 启动流程
        startProcess();
        // 修改变量
        modifyVariables();
        // 提交表单: 1. 提交表单; 2. 使用任务的 complete() 的方法提交
        submitForm();
        // 输出历史内容
        // 1. 输出历史活动
        outputHistoryActivities();
        // 2. 历史变量
        outputHistoryVariables();
        // 3. 输出历史用户任务
        outputHistoryTasks();
        // 4. 输出历史表单
        outputHistoryForms();
        // 5. 输出历史详情
        outputHistoryDetails();
    }

    private void outputHistoryDetails() {
        List<HistoricDetail> historicDetails = activitiRule.getHistoryService()
                .createHistoricDetailQuery().listPage(0, 100);
        for (HistoricDetail historicDetail : historicDetails) {
            logger.info("historicDetail = {}", StringUtils.toShortPrefixString(historicDetail));
        }
        logger.info("historicDetails size {}", historicDetails.size());
    }

    private void outputHistoryForms() {
        List<HistoricDetail> historicDetailsForms = activitiRule.getHistoryService()
                .createHistoricDetailQuery()
                .formProperties()
                .listPage(0, 100);
        for (HistoricDetail historicDetail : historicDetailsForms) {
            logger.info("historicDetail = {}", StringUtils.toShortPrefixString(historicDetail));
        }
        logger.info("historicDetailsForm size {}", historicDetailsForms.size());
    }

    private void outputHistoryTasks() {
        List<HistoricTaskInstance> historicTaskInstances = activitiRule.getHistoryService()
                .createHistoricTaskInstanceQuery().listPage(0, 100);
        for (HistoricTaskInstance historicTaskInstance : historicTaskInstances) {
            logger.info("historicTaskInstance = {}", historicTaskInstance);
        }
        logger.info("historicTaskInstances size {}", historicTaskInstances.size());
    }

    private void outputHistoryVariables() {
        List<HistoricVariableInstance> historicVariableInstances = activitiRule.getHistoryService()
                .createHistoricVariableInstanceQuery().listPage(0, 100);
        for (HistoricVariableInstance historicVariableInstance : historicVariableInstances) {
            logger.info("historicVariableInstance = {}", historicVariableInstance);
        }
        logger.info("historicVariableInstances size {}", historicVariableInstances.size());
    }

    private void outputHistoryActivities() {
        List<HistoricActivityInstance> historicActivityInstances = activitiRule.getHistoryService()
                .createHistoricActivityInstanceQuery().listPage(0, 100);
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            logger.info("historicActivityInstance = {}", historicActivityInstance);
        }
        logger.info("historicActivityInstance size {}", historicActivityInstances.size());
    }

    private void submitForm() {
        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
        Map<String, String> properties = Maps.newHashMap();
        properties.put("formKey1", "valuef1");
        properties.put("formKey2", "valuef2");
        activitiRule.getFormService().submitTaskFormData(task.getId(), properties);
    }

    private void modifyVariables() {
        List<Execution> executions = activitiRule.getRuntimeService()
                .createExecutionQuery().listPage(0, 100);
        for (Execution execution : executions) {
            logger.info("execution = {}", execution);
        }
        logger.info("executions size = {}", executions.size());
        String id = executions.iterator().next().getId();
        activitiRule.getRuntimeService().setVariable(id, "keyStart1", "value1_");
    }

    private void startProcess() {
        Map<String, Object> params = Maps.newHashMap();
        params.put("keyStart1", "value1");
        params.put("keyStart2", "value2");

        ProcessInstance processInstance = activitiRule.getRuntimeService()
                .startProcessInstanceByKey("my-process");
    }
}