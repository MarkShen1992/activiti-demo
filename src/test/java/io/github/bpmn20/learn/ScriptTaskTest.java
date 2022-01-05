package io.github.bpmn20.learn;

import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.collections.Maps;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * 脚本任务
 * JUEL(Default)
 * Groovy脚本
 * JavaScript脚本
 *
 * @author shenjunyu
 * @email sjy13149@cnki.net
 * @since 2022/1/5 9:00
 */
public class ScriptTaskTest {

    private static final Logger logger = LoggerFactory.getLogger(BPMN20Test.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @Test
    @Deployment(resources = {"bpmn2.0_groovy_script_task.bpmn20.xml"})
    public void testScriptTaskGroovy() {
        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");
        HistoryService historyService = activitiRule.getHistoryService();
        List<HistoricVariableInstance> historicVariableInstances = historyService.createHistoricVariableInstanceQuery()
                .processInstanceId(processInstance.getId())
                .orderByVariableName().asc()
                .listPage(0, 100);
        for (HistoricVariableInstance historicVariableInstance : historicVariableInstances) {
            logger.info("variable = {}", historicVariableInstance);
        }
        logger.info("variable size = {}", historicVariableInstances.size());
    }

    @Test
    @Deployment(resources = {"bpmn2.0_juel_script_task.bpmn20.xml"})
    public void testScriptTaskJUEL() {
        Map<String, Object> variables = Maps.newHashMap();
        variables.put("key1", 1);
        variables.put("key2", 2);
        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process", variables);
        HistoryService historyService = activitiRule.getHistoryService();
        List<HistoricVariableInstance> historicVariableInstances = historyService.createHistoricVariableInstanceQuery()
                .processInstanceId(processInstance.getId())
                .orderByVariableName().asc()
                .listPage(0, 100);
        for (HistoricVariableInstance historicVariableInstance : historicVariableInstances) {
            logger.info("variable = {}", historicVariableInstance);
        }
        logger.info("variable size = {}", historicVariableInstances.size());
    }

    @Test
    @Deployment(resources = {"bpmn2.0_js_script_task.bpmn20.xml"})
    public void testScriptTaskJs() {
        Map<String, Object> variables = Maps.newHashMap();
        variables.put("key1", 1);
        variables.put("key2", 2);
        ProcessInstance processInstance = activitiRule.getRuntimeService()
                .startProcessInstanceByKey("my-process", variables);
        HistoryService historyService = activitiRule.getHistoryService();
        List<HistoricVariableInstance> historicVariableInstances = historyService.createHistoricVariableInstanceQuery()
                .processInstanceId(processInstance.getId())
                .orderByVariableName().asc()
                .listPage(0, 100);
        for (HistoricVariableInstance historicVariableInstance : historicVariableInstances) {
            logger.info("variable = {}", historicVariableInstance);
        }
        logger.info("variable size = {}", historicVariableInstances.size());
    }

    /**
     * 写脚本要注意安全问题，互相review下代码
     *
     * @throws ScriptException
     */
    @Test
    @Deployment(resources = {"bpmn2.0_js_script_task.bpmn20.xml"})
    public void testScriptEngine() throws ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("juel");
        Object eval = scriptEngine.eval("#{1 + 2}");
        if (eval instanceof Long) {
            assertEquals(3L, eval);
        } else {
            logger.info("result = {}", eval);
        }
    }
}