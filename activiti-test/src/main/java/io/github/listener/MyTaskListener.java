package io.github.listener;

import com.google.common.collect.Lists;
import io.github.helloworld.DemoMain;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shenjunyu
 * @email sjy13149@cnki.net
 * @since 2022/1/5 8:44
 */
public class MyTaskListener implements TaskListener {
    private static final long serialVersionUID = -369393684357457889L;

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoMain.class);

    @Override
    public void notify(DelegateTask delegateTask) {
        String eventName = delegateTask.getEventName();
        if (StringUtils.equals("create", eventName)) {
            LOGGER.info("config by listener");
            delegateTask.addCandidateGroup("group1");
            delegateTask.addCandidateUsers(Lists.newArrayList("user1", "user2"));
            delegateTask.setVariable("key1", "value1");
            delegateTask.setDueDate(DateTime.now().plusDays(3).toDate());
        } else if (StringUtils.equals("complete", eventName)) {
            LOGGER.info("task complete.");
        }
    }
}
