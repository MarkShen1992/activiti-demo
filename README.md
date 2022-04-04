## 工作流学习项目

### 参考资料

- https://www.activiti.org/
- https://activiti.gitbook.io/activiti-7-developers-guide/

### 工作流平台搭建

- 需求分析

  - 满足流程运行的用户及权限管理功能
  - 管理角度的监控流程运行过程和历史数据
  - 方便与第三方系统集成

- 技术方案

  - 基于 activiti-ui 工程升级开发比较好
  - 基于 activiti-engine 从0开发

- 修改软件版本

  ```bash
  cd .../module/activiti-spring-boot
  mvn versions:set -DnewVersion=6.0.0-boot2
  mvn clean install source:jar -Dmaven.test.skip=true
  ```

  > IDEAJ 全项目替换 **replace in path**
  >
  > 在编译 `activiti-ui` 工程的时候，使用 `JDK1.8`, 使用`JDK1.11`会出现问题

- 使用Java开发的时候一定要安装插件 Maven Helper。