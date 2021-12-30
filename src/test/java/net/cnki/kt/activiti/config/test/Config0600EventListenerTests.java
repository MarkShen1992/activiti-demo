package net.cnki.kt.activiti.config.test;

import net.cnki.kt.activiti.event.CustomizeEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEventImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config0600EventListenerTests {

    private static final Logger logger = LoggerFactory.getLogger(Config0600EventListenerTests.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti_eventlistener.cfg.xml");

    /**
     * 根据资源文件去创建
     *
     * @see ActivitiEventType
     */
    @Test
    @Deployment(resources = {"my-process.bpmn20.xml"})
    public void testConfigEventListener() {
        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");
        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
        activitiRule.getTaskService().complete(task.getId());

        // 手动发出事件
        activitiRule.getRuntimeService().dispatchEvent(new ActivitiEventImpl(ActivitiEventType.CUSTOM));
    }

    @Test
    @Deployment(resources = {"my-process.bpmn20.xml"})
    public void testConfigEventListener2() {
        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");
        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
        activitiRule.getTaskService().complete(task.getId());

        // 手动发出事件, 观察者模式
        activitiRule.getRuntimeService().addEventListener(new CustomizeEventListener());
        activitiRule.getRuntimeService().dispatchEvent(new ActivitiEventImpl(ActivitiEventType.CUSTOM));
    }
}