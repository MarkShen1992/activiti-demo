package io.github.core.api;

import com.google.common.collect.Maps;
import io.github.utils.StringUtils;
import org.activiti.engine.FormService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNull;

/**
 * FormServiceTest: 自定义表单的操作
 *
 * @author shenjunyu
 * @email sjy13149@cnki.net
 * @since 2021/12/31 14:24
 */
public class FormServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(FormServiceTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @Test
    @Deployment(resources = {"my-process_form.bpmn20.xml"})
    public void testFormService() {
        FormService formService = activitiRule.getFormService();
        ProcessDefinition processDefinition =
            activitiRule.getRepositoryService().createProcessDefinitionQuery().singleResult();
        String startFormKey = formService.getStartFormKey(processDefinition.getId());
        logger.info("startFormKey = {}", startFormKey);

        StartFormData startFormData = formService.getStartFormData(processDefinition.getId());
        logger.info("startFormData = {}", StringUtils.toJSONString(startFormData));
        List<FormProperty> formProperties = startFormData.getFormProperties();
        for (FormProperty formProperty : formProperties) {
            logger.info("formProperty = {}", StringUtils.toJSONString(formProperty));
        }
        // 启动流程
        Map<String, String> params = Maps.newHashMap();
        params.put("message", "form test.");
        ProcessInstance processInstance = formService.submitStartFormData(processDefinition.getId(), params);
        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormProperty> formDataFormProperties = taskFormData.getFormProperties();
        for (FormProperty formDataFormProperty : formDataFormProperties) {
            logger.info("formDataFormProperty = {}", StringUtils.toJSONString(formDataFormProperty));
        }
        HashMap<String, String> map = Maps.newHashMap();
        map.put("yesORno", "yes");
        formService.submitTaskFormData(task.getId(), map);
        Task task1 = activitiRule.getTaskService().createTaskQuery().taskId(task.getId()).singleResult();
        assertNull(task1);
    }
}
