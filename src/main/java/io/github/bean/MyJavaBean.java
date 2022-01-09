package io.github.bean;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author MarkShen
 */
public class MyJavaBean implements Serializable {

    private static final long serialVersionUID = -415749306754582650L;
    private static final Logger LOGGER = LoggerFactory.getLogger(MyJavaBean.class);

    private String name;

    public MyJavaBean() {}

    public MyJavaBean(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void sayHello() {
        LOGGER.info("{} sayHello...", this.name);
    }
}