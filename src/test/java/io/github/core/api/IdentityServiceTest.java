package io.github.core.api;

import io.github.utils.StringUtils;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * IdentityServiceTest
 *
 * @author shenjunyu
 * @email sjy13149@cnki.net
 * @since 2021/12/31 14:24
 */
public class IdentityServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(IdentityService.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @Test
    public void testIdentity() {
        IdentityService identityService = activitiRule.getIdentityService();
        User user1 = identityService.newUser("user1");
        user1.setEmail("user1@126.com");
        identityService.saveUser(user1);

        User user2 = identityService.newUser("user2");
        user2.setEmail("user2@126.com");
        identityService.saveUser(user2);

        Group group1 = identityService.newGroup("group1");
        identityService.saveGroup(group1);
        Group group2 = identityService.newGroup("group2");
        identityService.saveGroup(group2);

        identityService.createMembership("user1", "group1");
        identityService.createMembership("user2", "group1");
        identityService.createMembership("user1", "group2");

        User user11 = identityService.createUserQuery().userId("user1").singleResult();
        user11.setLastName("Shen");
        identityService.saveUser(user11);

        List<User> userList = identityService.createUserQuery().memberOfGroup("group1").listPage(0, 100);
        for (User user : userList) {
            logger.info("user = {}", StringUtils.toJSONString(user));
        }

        List<Group> groups = identityService.createGroupQuery().groupMember("user1").listPage(0, 100);
        for (Group group : groups) {
            logger.info("group = {}", StringUtils.toJSONString(group));
        }
    }
}
