#### 1.简述目的
本教程可以学到： 
 * Flyway 与 Liquibase 相同点
 * Flyway 与 Liquibase 不同点 

#### 2.Flyway介绍
> Increase reliability of deployments by
versioning your database

Flyway 是通过数据库版本来提高软件部署可靠性的工具

#### 3.Liquibase介绍
>Join the millions of developers using Liquibase to version, track, and deploy database changes faster and safer. Increase productivity, deliver more frequently, and standardize the way you make database changes across all of your databases with Liquibase

Liquibase 是让你数据库的更新以标准的方式同步到你所有的数据库的工具

#### 4.Flyway 与 Liquibase 相同点

* 它们最主要功能都是用于**数据库更新的同步**
* 支持SQL语句
* 支持Spring Boot 集成
* 支持命令行
* 基于**版本**实现数据库更新
* 实现《数据库重构》一书提出概念
* 支持 Maven 和 Gradle 打包工具

#### 5.Flyway 与 Liquibase 不同点

|功能|Flyway|Liquibase|
|:--|:--|:--|
|更新文件|基于SQL、[基于java](https://documentation.red-gate.com/fd/tutorial-java-based-migrations-184127624.html)|[基于SQL,XML,JSON,YAML](https://docs.liquibase.com/start/get-started/liquibase-sql.html)|
|回滚|[付费](https://documentation.red-gate.com/fd/tutorial-undo-migrations-184127627.html)|[yes](https://docs.liquibase.com/commands/rollback/rollback-by-tag.html)|
|文件方式|[一个文件一个版本](https://documentation.red-gate.com/fd/migrations-184127470.html)|[不限制文件](https://docs.liquibase.com/concepts/changelogs/changeset.html)|