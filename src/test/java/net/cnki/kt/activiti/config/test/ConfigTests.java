package net.cnki.kt.activiti.config.test;

import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConfigTests {

    private static final Logger logger = LoggerFactory.getLogger(ConfigTests.class);

    /**
     * 根据资源文件去创建
     */
    @Test
    void testConfig1() {
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration
                .createProcessEngineConfigurationFromResourceDefault();

        logger.info("configuration = {}", configuration);
    }

    /**
     * 直接创建
     */
    @Test
    void testConfig2() {
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration
                .createStandaloneProcessEngineConfiguration();

        logger.info("configuration = {}", configuration);
    }
}
