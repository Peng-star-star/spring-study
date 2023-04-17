# 数据库-增量升级
  
#### 1.简述目的
  * 数据库更新同步功能实际案例
  * 选择 Flyway 还是 Liquibase

#### 2.案例与问题
假设我们有一个 Shiny 项目，其交付成果为 Shiny Soft 软件与 Shiny SQL 文件，该软件连接到Shiny DB数据库。有三个网络环境，分别是研发内网、A地区内网、B地区内网。

![原始交付流程图](https://github.com/Peng-star-star/spring-study/blob/main/image/004-%E6%B5%81%E7%A8%8B%E5%9B%BE.jpg)

Shiny SQL Version 1.0.0 文件为 `Shiny-1.0.0.sql`
```SQL
create table PERSON (
  ID int not null,
  NAME varchar(100) not null
);
```

Shiny SQL Version 1.0.1 文件为 `Shiny-1.0.1.sql`
```SQL
insert into PERSON (ID, NAME) values (1, 'Axel');
insert into PERSON (ID, NAME) values (2, 'Mr. Foo');
insert into PERSON (ID, NAME) values (3, 'Ms. Bar');
```

上面交付软件存在以下问题： 
  * 部署需一个一个版本执行SQL文件，不可跳版本升级
  * 生产环境快速修复，如何同步数据库修改到其他环境

#### 3.需求分析
  |需求|描述|
  |:--|:--|
  |软件启动时数据库自动升级|可减少部署操作，提高交付质量|
  |软件跳版本升级|降低软件部署限制|
  |版本回退|升级失败时回退机制|
  |查看数据库版本|查看数据库状态（版本），便于排查问题|
  |生产环境快速修复并同步|生产环境出现问题，需马上修改数据库。在生产环境相互网络隔离条件下如何发布到其他生产环境数据库，以减少修改遗忘问题。|
  

#### 4.解决方案
  |需求|Flyway|Liquibase|DataSourceInitializer|
  |:--|:--|:--|:--|
  |软件启动时数据库自动升级|yes|yes|SQL写法复杂|
  |软件跳版本升级|yes|yes|SQL写法复杂|
  |版本回退|**收费**|yes|no|
  |查看数据库版本|yes|yes|no|
  |生产环境快速修复并同步|no(网络隔离)|no(网络隔离)|no|
  
如何解决**生产环境快速修复**，Liquibase 可以参考下面流程：  
![生产环境快速修复图](https://github.com/Peng-star-star/spring-study/blob/main/image/004-%E7%94%9F%E4%BA%A7%E7%8E%AF%E5%A2%83%E5%BF%AB%E9%80%9F%E4%BF%AE%E5%A4%8D.jpg)
简单来说，通过 Liquibase 执行 update 修复生产环境数据库问题，并将 changelog files 发送到研发环境，合并代码，测试环境验证，再下发到其他生产环境。

#### 5.功能实现
 + Spring Boot 集成 Liquibase ,可以实现： 
   - 软件启动时数据库自动升级
   - 软件跳版本升级  
 + 安装 Liquibase，通过命令行执行 rollback，实现版本回退
 + 查询 DATABASECHANGELOG 表，实现查看数据库版本
 + 安装 Liquibase，通过命令行执行 update，快速修复生产环境问题；通过发送 changelog files 来同步到其他环境

最终发布流程图如下：
![最终发布流程图](https://github.com/Peng-star-star/spring-study/blob/main/image/004-%E5%8A%9F%E8%83%BD%E5%AE%9E%E7%8E%B0%E5%90%8E%E5%8F%91%E5%B8%83%E6%B5%81%E7%A8%8B%E5%9B%BE.jpg)

其中交付物为：
 * Shiny Soft
 * changelog files（版本回退与生产环境快速修复时使用）
   
> 