# activiti-demo
> 工作流学习

[how to ask question the smart way.][2]

本项目的是学习工作流引擎 [activiti](https://www.activiti.org/), 本项目使用的是 activiti6.0。

## 画图工具

本项目使用 eclipse 中的 activiti 画图插件来制作工作流，然后再导入到 IdeaJ 中使用。

> Make sure that the file **ends with .bpmn20.xml or .bpmn**, since otherwise the engine won’t pick up this file for deployment.

### [插件安装][1]

**Name**: Activiti BPMN 2.0 designer

**Location**: http://activiti.org/designer/update/

## 原理图

![工作流引擎原理图](./images/api.services.png)



- 官方文档：
  - https://www.activiti.org/userguide/
  - https://www.activiti.org/javadocs/

- 数据库配置

  - 缺省配置默认，使用H2内存数据库
  - 配置JDBC属性，使用Mybatis提供的连接池
  - 配置DataSource，可自选第三方实现

- 数据库支持

  | **Activiti database type** | **Example JDBC URL**                                         | **Notes**                                                    |
  | -------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
  | h2                         | jdbc:h2:tcp://localhost/activiti                             | Default configured database                                  |
  | mysql                      | jdbc:mysql://localhost:3306/activiti?autoReconnect=true      | Tested using mysql-connector-java database driver            |
  | oracle                     | jdbc:oracle:thin:@localhost:1521:xe                          |                                                              |
  | postgres                   | jdbc:postgresql://localhost:5432/activiti                    |                                                              |
  | db2                        | jdbc:db2://localhost:50000/activiti                          |                                                              |
  | mssql                      | jdbc:sqlserver://localhost:1433;databaseName=activiti (jdbc.driver=com.microsoft.sqlserver.jdbc.SQLServerDriver) *OR* jdbc:jtds:sqlserver://localhost:1433/activiti (jdbc.driver=net.sourceforge.jtds.jdbc.Driver) | Tested using Microsoft JDBC Driver 4.0 (sqljdbc4.jar) and JTDS Driver |

- 数据库更新策略

  - false: 启动时检查数据库版本，发生不匹配抛异常（**默认**）
  - true: 启动时自动检查并更新数据库表，不存在会创建
  - create-drop: 启动时创建数据库表结构，结束时删除表结构(适合用于单元测试场景)

- MySQL 数据库连接驱动

  ```
  MySQL 5.7: com.mysql.jdbc.Driver
  MySQL 8: com.mysql.cj.jdbc.Driver
  
  jdbcUrl: jdbc:mysql://{ip}:{port}/{db}?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true
  ```

- 数据源配置

  ```xml
  <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
          <property name="url"
                    value="jdbc:mysql://localhost:3306/activiti6unit?characterEncoding=utf8&amp;useSSL=false&amp;serverTimezone=UTC&amp;rewriteBatchedStatements=true"/>
          <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
          <property name="username" value="root"/>
          <property name="password" value="root"/>
          <property name="initialSize" value="1"/>
          <property name="maxActive" value="10"/>
          <property name="filters" value="stat, slf4j"/>
      </bean>
  ```

- 作业执行器的配置
  - timeDate: 指定启动时间
  - timeDuration: 指定持续时间间隔后执行
  - timeCycle:R5/P1DT1H 指定事件段后周期执行

### 各种 Service 的使用场景

#### 1. RepositoryService

> RepositoryService的主要功能是：



#### 2. TaskService

> TaskService的主要功能是：



#### 3. IdentityService

> IdentityService的主要功能是：



#### 4. FormService

> FormService的主要功能是：



#### 5. RunTimeService

> RunTimeService的主要功能是：



#### 6. ManagementService

> ManagementService的主要功能是：



#### 7. HistoryService

> HistoryService的主要功能是：









[1]:https://www.activiti.org/userguide/#eclipseDesignerInstallation
[2]:http://www.catb.org/~esr/faqs/smart-questions.html
