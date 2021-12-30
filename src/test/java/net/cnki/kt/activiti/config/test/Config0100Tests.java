package net.cnki.kt.activiti.config.test;

import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config0100Tests {

    private static final Logger logger = LoggerFactory.getLogger(Config0100Tests.class);

    /**
     * 根据资源文件去创建
     */
    @Test
    public void testConfig1() {
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration
                .createProcessEngineConfigurationFromResourceDefault();

        logger.info("configuration = {}", configuration);
    }

    /**
     * 直接创建
     */
    @Test
    public void testConfig2() {
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration
                .createStandaloneProcessEngineConfiguration();

        logger.info("configuration = {}", configuration);
    }
}
