# e3mall
这里是e3商城

组织结构
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

技术选型

环境搭建

启动顺序

常见问题