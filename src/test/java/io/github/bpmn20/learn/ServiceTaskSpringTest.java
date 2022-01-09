package io.github.bpmn20.learn;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Maps;

import io.github.bean.MyJavaBean;
import io.github.delegate.MyJavaDelegate;

/**
 * @author shenjunyu
 * @email sjy13149@cnki.net
 * @since 2022/1/5 17:24
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:activiti-context_2.xml")
public class ServiceTaskSpringTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceTaskSpringTest.class);

    @Resource
    @Rule
    public ActivitiRule activityRule;

    /**
     * DelegateExpression: 同一个实例对象，注入属性非线程安全； activiti:class 每次创建一个新的对象，注入属性线程安全
     */
    @Test
    @Deployment(resources = {"bpmn2.0_service_task_4.bpmn20.xml"})
    public void testServiceTask() {
        ProcessInstance processInstance = activityRule.getRuntimeService().startProcessInstanceByKey("my-process");
        List<HistoricActivityInstance> historicActivityInstances = activityRule.getHistoryService()
            .createHistoricActivityInstanceQuery().orderByHistoricActivityInstanceEndTime().asc().list();
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            LOGGER.info("historicActivityInstance = {}", historicActivityInstance);
        }
        LOGGER.info("historicActivityInstances size = {}", historicActivityInstances.size());
    }

    @Test
    @Deployment(resources = {"bpmn2.0_service_task_4.bpmn20.xml"})
    public void testServiceTask2() {
        Map<String, Object> variables = Maps.newHashMap();
        MyJavaDelegate myJavaDelegate = new MyJavaDelegate();
        // 先选择我们传入的参数，如果没有从Spring容器中取
        variables.put("myJavaDelegate", myJavaDelegate);
        LOGGER.info("myJavaDelegate = {}", myJavaDelegate);
        ProcessInstance processInstance =
            activityRule.getRuntimeService().startProcessInstanceByKey("my-process", variables);
        List<HistoricActivityInstance> historicActivityInstances = activityRule.getHistoryService()
            .createHistoricActivityInstanceQuery().orderByHistoricActivityInstanceEndTime().asc().list();
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            LOGGER.info("historicActivityInstance = {}", historicActivityInstance);
        }
        LOGGER.info("historicActivityInstances size = {}", historicActivityInstances.size());
    }

    @Test
    @Deployment(resources = {"bpmn2.0_service_task_5.bpmn20.xml"})
    public void testServiceTask3() {
        Map<String, Object> variables = Maps.newHashMap();
        MyJavaBean myJavaBean = new MyJavaBean("shenjy");
        // 先选择我们传入的参数，如果没有从Spring容器中取
        variables.put("myJavaBean", myJavaBean);
        LOGGER.info("myJavaBean = {}", myJavaBean);
        ProcessInstance processInstance =
            activityRule.getRuntimeService().startProcessInstanceByKey("my-process", variables);
        List<HistoricActivityInstance> historicActivityInstances = activityRule.getHistoryService()
            .createHistoricActivityInstanceQuery().orderByHistoricActivityInstanceEndTime().asc().list();
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            LOGGER.info("historicActivityInstance = {}", historicActivityInstance);
        }
        LOGGER.info("historicActivityInstances size = {}", historicActivityInstances.size());
    }
}