package io.github.config.test;

import org.activiti.engine.runtime.Job;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Config0800JobTests {

    private static final Logger logger = LoggerFactory.getLogger(Config0800JobTests.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti_job.cfg.xml");

    @Test
    @Deployment(resources = {"my-process_job.bpmn20.xml"})
    public void testConfigEventLog() throws InterruptedException {
        logger.info("start");
        List<Job> jobs = activitiRule.getManagementService().createTimerJobQuery().listPage(0, 100);
        for (Job job : jobs) {
            logger.info("定时任务 = {}, 重试次数 = {}", job, job.getRetries());
        }
        logger.info("jobs.size = {}", jobs.size());
        Thread.sleep(1000 * 60);
        logger.info("end");
    }
}