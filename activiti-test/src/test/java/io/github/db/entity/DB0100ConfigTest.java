package io.github.db.entity;

import com.google.common.collect.Lists;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 表相关
 *
 * @author shenjunyu
 * @email sjy13149@cnki.net
 * @since 2022/1/4 8:22
 */
public class DB0100ConfigTest {

    private static final Logger logger = LoggerFactory.getLogger(DB0100ConfigTest.class);

    @Test
    public void testDefaultDBConfig() {
        ProcessEngine processEngine =
            ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault().buildProcessEngine();
        ManagementService managementService = processEngine.getManagementService();
        Map<String, Long> tableCount = managementService.getTableCount();
        List<String> tableNames = Lists.newArrayList(tableCount.keySet());
        Collections.sort(tableNames);
        for (String tableName : tableNames) {
            logger.info("tableName = {}", tableName);
        }
        logger.info("table sizes = {}", tableNames.size());
    }

    @Test
    public void testMySQLDBConfig() {
        ProcessEngine processEngine = ProcessEngineConfiguration
            .createProcessEngineConfigurationFromResource("activiti-mysql.cfg.xml").buildProcessEngine();
        ManagementService managementService = processEngine.getManagementService();
        Map<String, Long> tableCount = managementService.getTableCount();
        List<String> tableNames = Lists.newArrayList(tableCount.keySet());
        Collections.sort(tableNames);
        for (String tableName : tableNames) {
            logger.info("tableName = {}", tableName);
        }
        logger.info("table sizes = {}", tableNames.size());
    }

    @Test
    public void testRemoveTableSchema() {
        ProcessEngine processEngine = ProcessEngineConfiguration
            .createProcessEngineConfigurationFromResource("activiti-mysql.cfg.xml").buildProcessEngine();
        ManagementService managementService = processEngine.getManagementService();
        managementService.executeCommand(commandContext -> {
            commandContext.getDbSqlSession().dbSchemaDrop();
            logger.info("remove table schema...");
            return null;
        });
    }
}
