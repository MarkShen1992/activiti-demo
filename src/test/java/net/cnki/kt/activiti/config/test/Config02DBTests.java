package net.cnki.kt.activiti.config.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config02DBTests {

    private static final Logger logger = LoggerFactory.getLogger(Config02DBTests.class);

    /**
     * 根据资源文件去创建
     */
    @Test
    public void testConfigDB1() {
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration
                .createProcessEngineConfigurationFromResourceDefault();

        logger.info("configuration = [{}]", configuration);
        ProcessEngine processEngine = configuration.buildProcessEngine();
        logger.info("获取流程引擎 [{}]", processEngine.getName());
        processEngine.close();
    }

    /**
     * 使用 MySQL 数据库存储相关数据
     * 在执行本测试用例的时候，首先要做的是在本地环境中安装 MySQL 8.0 数据库
     * 并创建空数据库 activiti6unit
     */
    @Test
    public void testConfigDB2() {
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration
                .createProcessEngineConfigurationFromResource("activiti_druid.cfg.xml");

        logger.info("configuration = [{}]", configuration);
        ProcessEngine processEngine = configuration.buildProcessEngine();
        logger.info("获取流程引擎 [{}]", processEngine.getName());
        processEngine.close();
    }
}
