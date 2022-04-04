package io.github.db.entity;

import org.activiti.engine.ManagementService;
import org.activiti.engine.impl.persistence.entity.ByteArrayEntityImpl;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * General Table
 *
 * @author shenjunyu
 * @email sjy13149@cnki.net
 * @since 2022/1/4 8:22
 */
public class DB0200GETest {

    private static final Logger logger = LoggerFactory.getLogger(DB0200GETest.class);

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
    public void testByteArrays() {
        activitiRule.getRepositoryService().createDeployment().name("test deployment...")
            .addClasspathResource("my-process.bpmn20.xml").deploy();
    }

    @Test
    public void testByteArrayInsert() {
        ManagementService managementService = activitiRule.getManagementService();
        managementService.executeCommand(commandContext -> {
            ByteArrayEntityImpl byteArrayEntity = new ByteArrayEntityImpl();
            byteArrayEntity.setName("test");
            byteArrayEntity.setBytes("test message".getBytes());
            commandContext.getByteArrayEntityManager().insert(byteArrayEntity);
            return null;
        });
    }
}
