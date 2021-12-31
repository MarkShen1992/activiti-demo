package io.github.core.api;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * RepositoryService Test
 *
 * @author shenjunyu
 * @email sjy13149@cnki.net
 * @since 2021/12/31 7:58
 */
public class RepositoryServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(RepositoryServiceTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @Test
    public void testRepositoryDeployOnce() {
        RepositoryService repositoryService = activitiRule.getRepositoryService();
        assertNotNull(repositoryService);
        DeploymentBuilder deployment = repositoryService.createDeployment();
        deployment.name("test deployment resources.")
                .addClasspathResource("my-process.bpmn20.xml")
                .addClasspathResource("second_approve.bpmn20.xml");
        Deployment deploy = deployment.deploy();
        logger.info("deploy = {}", deploy);

        DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
        Deployment deploy01 = deploymentQuery.deploymentId(deploy.getId()).singleResult();

        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploy.getId()).listPage(0, 100);
        for (ProcessDefinition processDefinition : processDefinitions) {
            logger.info("processDefinition = {}, version = {}, processKey = {}, processId = {}",
                    processDefinition, processDefinition.getVersion(),
                    processDefinition.getKey(), processDefinition.getId());
        }
        logger.info("processDefinition size = {}", processDefinitions.size());
    }

    @Test
    public void testRepositoryDeployManyTimes() {
        RepositoryService repositoryService = activitiRule.getRepositoryService();
        assertNotNull(repositoryService);
        DeploymentBuilder deployment = repositoryService.createDeployment();
        deployment.name("test deployment resources.1")
                .addClasspathResource("my-process.bpmn20.xml")
                .addClasspathResource("second_approve.bpmn20.xml");
        Deployment deploy01 = deployment.deploy();
        logger.info("deploy = {}", deploy01);

        // the second deployment.
        DeploymentBuilder deployment2 = repositoryService.createDeployment();
        deployment2.name("test deployment resources.2")
                .addClasspathResource("my-process.bpmn20.xml")
                .addClasspathResource("second_approve.bpmn20.xml");
        Deployment deploy02 = deployment2.deploy();
        logger.info("deploy = {}", deploy02);

        DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
        List<Deployment> deployments = deploymentQuery.orderByDeploymenTime().asc().listPage(0, 100);

        for (Deployment deploy : deployments) {
            logger.info("deployment = {}", deploy);
        }
        logger.info("deploymentList.size = {}", deployments.size());

        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
                .orderByProcessDefinitionKey().asc().listPage(0, 100);
        for (ProcessDefinition processDefinition : processDefinitions) {
            logger.info("processDefinition = {}, version = {}, processKey = {}, processId = {}",
                    processDefinition, processDefinition.getVersion(),
                    processDefinition.getKey(), processDefinition.getId());
        }
        logger.info("processDefinition size = {}", processDefinitions.size());
    }

    @Test
    @org.activiti.engine.test.Deployment(resources = {"my-process.bpmn20.xml"})
    public void testSuspend() {
        RepositoryService repositoryService = activitiRule.getRepositoryService();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().singleResult();
        logger.info("processDefinition.id = {}", processDefinition.getId());
        repositoryService.suspendProcessDefinitionById(processDefinition.getId());

        try {
            logger.info("start");
            activitiRule.getRuntimeService().startProcessInstanceById(processDefinition.getId());
            logger.info("start successful");
        } catch (ActivitiException e) {
            logger.info("start fail.");
            logger.info(e.getMessage(), e);
        }

        repositoryService.activateProcessDefinitionById(processDefinition.getId());
        logger.info("start");
        activitiRule.getRuntimeService().startProcessInstanceById(processDefinition.getId());
        logger.info("start successful");
    }

    @Test
    @org.activiti.engine.test.Deployment(resources = {"my-process.bpmn20.xml"})
    public void testCandidateStarter() {
        RepositoryService repositoryService = activitiRule.getRepositoryService();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().singleResult();
        logger.info("processDefinition.id = {}", processDefinition.getId());
        repositoryService.addCandidateStarterUser(processDefinition.getId(), "user");
        repositoryService.addCandidateStarterGroup(processDefinition.getId(), "group_admin");

        List<IdentityLink> identityLinksForProcessDefinition = repositoryService
                .getIdentityLinksForProcessDefinition(processDefinition.getId());
        for (IdentityLink identityLink : identityLinksForProcessDefinition) {
            logger.info("identityLink = {}", identityLink);
        }

        // delete user and user group
        repositoryService.deleteCandidateStarterUser(processDefinition.getId(), "user");
        repositoryService.deleteCandidateStarterGroup(processDefinition.getId(), "group_admin");
    }
}
