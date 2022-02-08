package io.github.db.entity;

import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

/**
 * REPOSITORY Table
 *
 * @author shenjunyu
 * @email sjy13149@cnki.net
 * @since 2022/1/4 8:22
 */
public class DB0300RETest {

    private static final Logger logger = LoggerFactory.getLogger(DB0300RETest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti-mysql.cfg.xml");

    @Test
    public void testRemoveTableSchema() {
        ManagementService managementService = activitiRule.getManagementService();
        managementService.executeCommand(commandContext -> {
            commandContext.getDbSqlSession().dbSchemaDrop();
            logger.info("remove table schema...");
            return null;
        });
    }

    @Test
    public void testReDeploy() {
        activitiRule.getRepositoryService().createDeployment()
                .name("second audit process...")
                .addClasspathResource("second_approve.bpmn20.xml")
                .deploy();
    }

    @Test
    public void testSuspend() {
        RepositoryService repositoryService = activitiRule.getRepositoryService();
        repositoryService.suspendProcessDefinitionById("second_approve:2:7504");
        assertEquals(Boolean.TRUE, repositoryService.isProcessDefinitionSuspended("second_approve:2:7504"));
    }
}
