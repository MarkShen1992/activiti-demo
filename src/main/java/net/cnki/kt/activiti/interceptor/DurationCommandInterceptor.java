package net.cnki.kt.activiti.interceptor;

import org.activiti.engine.impl.interceptor.AbstractCommandInterceptor;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 命令执行时间
 *
 * @author shenjunyu
 * @email sjy13149@cnki.net
 * @since 2021/12/30 17:10
 */
public class DurationCommandInterceptor extends AbstractCommandInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(DurationCommandInterceptor.class);

    @Override
    public <T> T execute(CommandConfig commandConfig, Command<T> command) {
        long start = System.nanoTime();
        try {
            return this.getNext().execute(commandConfig, command);
        } finally {
            long end = System.nanoTime();
            logger.info("{} 执行时常 {} 纳秒", command.getClass().getSimpleName(), end - start);
        }
    }
}
