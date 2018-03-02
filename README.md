# e3mall
这里是e3商城

#### 项目介绍
	宜立方网上商城是一个综合性的B2C平台

#### 组织结构
```
e3-parent：父工程，打包方式pom，管理jar包的版本号。
    |           项目中所有工程都应该继承父工程。
    |--e3-common：通用的工具类通用的pojo。打包方式jar
    |--e3-manager：服务层工程。聚合工程。Pom工程
    |--e3-manager-dao：打包方式jar
    |--e3-manager-pojo：打包方式jar
    |--e3-manager-interface：打包方式jar
    |--e3-manager-service：打包方式：jar
    |--e3-manager-web：表现层工程。打包方式war
```

#### 技术选型

###### 后台技术
| 技术              | 名称      | 版本 |
| :--------------: | :-------: | :-----: |
| Spring Framework | 容器      | |
| SpringMVC        | MVC框架   | |

#### 开发工具

- Mysql:数据库
- Tomcat:应用服务器
- Git:版本控制
- Intellij IDEA:开发IDE
- Navicat:数据库客户端

#### 启动顺序（后台）

#### 常见问题