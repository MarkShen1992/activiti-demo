package io.github.workflow;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.activiti.app.conf.ApplicationConfiguration;
import org.activiti.app.servlet.ApiDispatcherServletConfiguration;
import org.activiti.app.servlet.AppDispatcherServletConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication(
    exclude = {SecurityAutoConfiguration.class, org.activiti.spring.boot.SecurityAutoConfiguration.class})
@Import({ApplicationConfiguration.class})
public class WorkflowApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(WorkflowApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WorkflowApplication.class);
    }

    @Bean
    public ServletRegistrationBean apiDispatcher() {
        DispatcherServlet api = new DispatcherServlet();
        api.setContextClass(AnnotationConfigWebApplicationContext.class);
        api.setContextConfigLocation(ApiDispatcherServletConfiguration.class.getName());

        ServletRegistrationBean srb = new ServletRegistrationBean();
        srb.setServlet(api);
        srb.addUrlMappings("/api/*");
        srb.setLoadOnStartup(1);
        srb.setAsyncSupported(true);
        srb.setName("api");
        return srb;
    }

    @Bean
    public ServletRegistrationBean appDispatcher() {
        DispatcherServlet app = new DispatcherServlet();
        app.setContextClass(AnnotationConfigWebApplicationContext.class);
        app.setContextConfigLocation(AppDispatcherServletConfiguration.class.getName());

        ServletRegistrationBean srb = new ServletRegistrationBean();
        srb.setServlet(app);
        srb.addUrlMappings("/app/*");
        srb.setLoadOnStartup(1);
        srb.setAsyncSupported(true);
        srb.setName("app");
        return srb;
    }

    @Bean
    public FilterRegistrationBean openEntityManagerInViewFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean(new OpenEntityManagerInViewFilter());
        bean.addUrlPatterns("/*");
        bean.setName("openEntityManagerInViewFilter");
        bean.setOrder(-200); // 顺序，优先级越小执行越早
        bean.setDispatcherTypes(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC));
        return bean;
    }
}
