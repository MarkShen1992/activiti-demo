package io.github.core.api;

import io.github.mapper.MyCustomMapper;
import io.github.utils.StringUtils;
import org.activiti.engine.ManagementService;
import org.activiti.engine.impl.cmd.AbstractCustomSqlExecution;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.management.TablePage;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * ManagementServiceTest
 *
 * @author shenjunyu
 * @email sjy13149@cnki.net
 * @since 2021/12/31 14:24
 */
public class ManagementServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(ManagementServiceTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti_job.cfg.xml");

    @Test
    @Deployment(resources = {"my-process_job.bpmn20.xml"})
    public void testTimerJobQuery() {
        ManagementService managementService = activitiRule.getManagementService();
        List<Job> jobs = managementService.createTimerJobQuery().listPage(0, 100);
        for (Job job : jobs) {
            logger.info("timer job = {}", StringUtils.toJSONString(job));
        }
    }

    @Test
    @Deployment(resources = {"my-process_job.bpmn20.xml"})
    public void testSuspendedJobQuery() {
        ManagementService managementService = activitiRule.getManagementService();
        List<Job> jobs = managementService.createSuspendedJobQuery().listPage(0, 100);
        for (Job job : jobs) {
            logger.info("suspended job = {}", StringUtils.toJSONString(job));
        }
    }

    @Test
    @Deployment(resources = {"my-process_job.bpmn20.xml"})
    public void testJobQuery() {
        ManagementService managementService = activitiRule.getManagementService();
        List<Job> jobs = managementService.createJobQuery().listPage(0, 100);
        for (Job job : jobs) {
            logger.info("job = {}", StringUtils.toJSONString(job));
        }
    }

    @Test
    @Deployment(resources = {"my-process_job.bpmn20.xml"})
    public void testDeadLetterJobQuery() {
        ManagementService managementService = activitiRule.getManagementService();
        List<Job> jobs = managementService.createDeadLetterJobQuery().listPage(0, 100);
        for (Job job : jobs) {
            logger.info("deadLetter job = {}", StringUtils.toJSONString(job));
        }
    }

    @Test
    @Deployment(resources = {"my-process_job.bpmn20.xml"})
    public void testTablePageQuery() {
        ManagementService managementService = activitiRule.getManagementService();
        TablePage tablePage = managementService.createTablePageQuery()
            .tableName(managementService.getTableName(ProcessDefinitionEntity.class)).listPage(0, 100);
        List<Map<String, Object>> rows = tablePage.getRows();
        for (Map<String, Object> row : rows) {
            logger.info("row info = {}", row);
        }
    }

    @Test
    @Deployment(resources = {"my-process_job.bpmn20.xml"})
    public void testExecuteCustomSQL() {
        activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");
        ManagementService managementService = activitiRule.getManagementService();
        List<Map<String, Object>> maps = managementService.executeCustomSql(
            new AbstractCustomSqlExecution<MyCustomMapper, List<Map<String, Object>>>(MyCustomMapper.class) {
                @Override
                public List<Map<String, Object>> execute(MyCustomMapper o) {
                    return o.findAll();
                }
            });
        for (Map<String, Object> map : maps) {
            logger.info("map info = {}", map);
        }
    }

    @Test
    @Deployment(resources = {"my-process_job.bpmn20.xml"})
    public void testExecuteActivitiCommand() {
        activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");
        ManagementService managementService = activitiRule.getManagementService();
        ProcessDefinitionEntity processDefinition =
            managementService.executeCommand(new Command<ProcessDefinitionEntity>() {
                @Override
                public ProcessDefinitionEntity execute(CommandContext commandContext) {
                    ProcessDefinitionEntity processDefinitionEntity = commandContext.getProcessDefinitionEntityManager()
                        .findLatestProcessDefinitionByKey("my-process");
                    return processDefinitionEntity;
                }
            });
        logger.info("processDefinition = {}", processDefinition);
    }

    @Test
    @Deployment(resources = {"my-process_job.bpmn20.xml"})
    public void testExecuteActivitiCommand2() {
        activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");
        ManagementService managementService = activitiRule.getManagementService();
        ProcessDefinitionEntity processDefinition = managementService.executeCommand(commandContext -> commandContext
            .getProcessDefinitionEntityManager().findLatestProcessDefinitionByKey("my-process"));
        logger.info("processDefinition = {}", processDefinition);
    }
}
