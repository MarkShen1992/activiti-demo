package io.github.bpmn20.learn;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.collections.Maps;

import java.util.List;
import java.util.Map;

/**
 * 子流程分类：常规子流程，事件子流程，调用子流程（推荐）
 * 子流程测试
 * 1. 分层建模
 * 2. 数据隔离
 * 3. 事件范围
 * 4. 边界事件
 *
 * @author shenjunyu
 * @email sjy13149@cnki.net
 * @since 2022/1/5 9:00
 */
public class SubProcessTest {

    private static final Logger logger = LoggerFactory.getLogger(SubProcessTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @Test
    @Deployment(resources = {"bpmn2.0_subprocess1.bpmn20.xml"})
    public void testSubProcess1() {
        Map<String, Object> vars = Maps.newHashMap();
        vars.put("errorFlag", false);
        ProcessInstance processInstance =
            activitiRule.getRuntimeService().startProcessInstanceByKey("my-process", vars);
        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
        logger.info("task.name = {}", task.getName());
    }

    @Test
    @Deployment(resources = {"bpmn2.0_subprocess1.bpmn20.xml"})
    public void testSubProcess2() {
        Map<String, Object> vars = Maps.newHashMap();
        vars.put("errorFlag", true);
        ProcessInstance processInstance =
                activitiRule.getRuntimeService().startProcessInstanceByKey("my-process", vars);
        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
        logger.info("task.name = {}", task.getName());

        Map<String, Object> variables = activitiRule.getRuntimeService().getVariables(processInstance.getId());
        logger.info("variables = {}", variables);
    }

    /**
     * 事件触发
     * 业务独立
     * 中断事件流程
     * 错误开始事件
     */
    @Test
    @Deployment(resources = {"bpmn2.0_event_subprocess1.bpmn20.xml"})
    public void testSubProcess3() {
        Map<String, Object> vars = Maps.newHashMap();
        vars.put("errorFlag", true);
        ProcessInstance processInstance =
                activitiRule.getRuntimeService().startProcessInstanceByKey("my-process", vars);
        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
        logger.info("task.name = {}", task.getName());

        Map<String, Object> variables = activitiRule.getRuntimeService().getVariables(processInstance.getId());
        logger.info("variables = {}", variables);
    }

    /**
     * 当发现结果与我们的猜想发生不一致的情况时，我们首先要做的是查看流程定义文件，然后通过修改
     * 可疑节点的 name 进行调试
     */
    @Test
    @Deployment(resources = {"bpmn2.0_main_process.bpmn20.xml", "bpmn2.0_main_subprocess.bpmn20.xml"})
    public void testCallSubProcess1() {
        Map<String, Object> vars = Maps.newHashMap();
        vars.put("errorFlag", false);
        vars.put("key0", "value0");
        ProcessInstance processInstance =
                activitiRule.getRuntimeService().startProcessInstanceByKey("my-process", vars);
        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
        logger.info("task.name = {}", task.getName());

        Map<String, Object> variables = activitiRule.getRuntimeService().getVariables(processInstance.getId());
        logger.info("variables = {}", variables);
    }

    @Test
    @Deployment(resources = {"bpmn2.0_main_process.bpmn20.xml", "bpmn2.0_main_subprocess.bpmn20.xml"})
    public void testCallSubProcess2() {
        Map<String, Object> vars = Maps.newHashMap();
        vars.put("errorFlag", true);
        vars.put("key0", "value0");
        ProcessInstance processInstance =
                activitiRule.getRuntimeService().startProcessInstanceByKey("my-process", vars);
        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
        logger.info("task.name = {}", task.getName());

        Map<String, Object> variables = activitiRule.getRuntimeService().getVariables(processInstance.getId());
        logger.info("variables = {}", variables);
    }
}