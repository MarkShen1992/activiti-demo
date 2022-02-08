package io.github.db.entity;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 身份数据库
 *
 * @author shenjunyu
 * @email sjy13149@cnki.net
 * @since 2022/1/4 8:22
 */
public class DB0400IDTest {

    private static final Logger logger = LoggerFactory.getLogger(DB0400IDTest.class);

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
    public void testIdentity() {
        IdentityService identityService = activitiRule.getIdentityService();
        User user1 = identityService.newUser("user1");
        user1.setFirstName("mark");
        user1.setLastName("shen");
        user1.setEmail("markshen@163.com");
        user1.setPassword("pwd");
        identityService.saveUser(user1);

        User user2 = identityService.newUser("user2");
        identityService.saveUser(user2);

        Group group1 = identityService.newGroup("group1");
        group1.setName("test group");
        identityService.saveGroup(group1);

        identityService.createMembership(user1.getId(), group1.getId());
        identityService.createMembership(user2.getId(), group1.getId());

        identityService.setUserInfo(user1.getId(), "age", "18");
        identityService.setUserInfo(user1.getId(), "address", "BeiJing");
    }
}
