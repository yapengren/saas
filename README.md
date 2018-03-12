# e3mall
这里是e3商城

### 项目介绍
	宜立方网上商城是一个综合性的B2C平台

### 组织结构
```
e3-parent -- 父工程，管理jar包的版本号
    |-- e3-common -- 工具类、pojo
    |-- e3-manager -- 商城管理系统
    |   |-- e3-manager-dao -- 代码生成模块
    |   |-- e3-manager-pojo -- 
    |   |-- e3-manager-interface -- rpc接口包
    |   |-- e3-manager-service -- rpc服务提供者[端口：9090]
    |-- e3-manager-web -- 商城管理系统表现层[端口：9091]
    |-- e3-portal-web -- 商城门户[端口：9092]
    |-- e3-content -- 内容管理系统
    |   |-- e3-content-interface -- rpc接口包
    |   |-- e3-content-service -- rpc服务提供者[端口：9093]
    |-- e3-search -- 搜索系统
    |	|-- e3-search-interface -- rpc接口包
    |	|-- e3-search-service -- rpc服务提供者[端口：]
```

### 技术选型

###### 后台技术

| 技术              | 名称         | 版本     |
| :--------------: | :----------: | :-----: |
| Spring Framework | 容器         | 4.2.4    |
| SpringMVC        | MVC框架      | 4.2.4    |
| MyBatis          | ORM框架      | 3.2.8    |
| MyBatis Generator | 代码生成     | IDEA Mybatis Plugin |
| PageHelper       | MyBatis物理分页插件 | 5.0.1 |
| Druid            | 数据库连接池   | 1.0.9   |
| Zookeeper        | 分布式协调服务 | 3.4.7   |
| Dubbo            | 分布式服务框架 | 2.5.3   |
| Redis            | 分布式缓存数据库 | 3.0.7 |
| Solr             | 分布式全文搜索引擎 | 4.10.3 |
| Quartz           | 作业调度框架   | 2.2.2  |
| ActiveMQ         | 消息队列      | 5.11.2 |
| FastDFS          | 分布式文件系统 | 1.25.4 |
| Maven            | 项目构建管理   | 3.3.9 |
 
### 架构图
![](https://i.imgur.com/lWAYzGS.jpg)

### 模块介绍
后台管理系统：管理商品、订单、类目、商品规格属性、用户管理、内容发布等功能
前台系统：用户可以在前台系统中进行注册、登录、浏览商品、首页、下单等操作
会员系统：用户可以在该系统中查询已下的订单、收藏的商品、我的优惠券、团购等信息
订单系统：提供下单、查询订单、修改订单状态、定时处理订单
搜索系统：提供商品的搜索功能
单点登录系统：为多个系统之间提供用户登录凭证以及查询登录用户的信息

#### 开发工具

- Mysql5.1:数据库
- Tomcat:应用服务器
- Git:版本控制
- Nginx:反向代理服务器
- Intellij IDEA:开发IDE
- Navicat:数据库客户端

### 开发指南

启动 MySQL、Redis、Zookeeper、Dubbo admin相关服务

##### 修改本地Host
- 127.0.0.1 dbserver
- 192.168.25.133 trackerserver
- 192.168.25.133 fdfsserver
- 192.168.25.128 zkserver

#### 常见问题
1. 项目启动报错，报错信息如下：
```
严重: Servlet.service() for servlet [e3-manager] in context with path [] threw exception [Request processing failed; 
	nested exception is org.apache.ibatis.binding.BindingException: Invalid bound statement (not found): com.yapengren.e3mall.mapper.TbItemMapper.selectByPrimaryKey] with root cause
    org.apache.ibatis.binding.BindingException: Invalid bound statement (not found): com.yapengren.e3mall.mapper.TbItemMapper.selectByPrimaryKey
```
此异常的原因是由于 mapper 接口编译后在同一个目录下没有找到 mapper 映射文件而出现的。由于 maven 工程在默认情况下 src/main/java 目录下的 mapper 文件是不发布到 target 目录下的。

解决方法：
pom.xml 文件内添加节点
```
<build>
	<resources>
        <resource>
            <directory>src/main/java</directory>
            <includes>
                <include>**/*.properties</include>
                <include>**/*.xml</include>
            </includes>
            <filtering>false</filtering>
        </resource>
    </resources>
</build>
```

2. 报错信息如下：
```
严重: Servlet.service() for servlet [e3-manager] in context with path [] threw exception [Request processing failed; nested exception is com.alibaba.dubbo.rpc.RpcException: Failed to invoke the method getItemById in the service com.yapengren.e3mall.service.ItemService. Tried 3 times of the providers [10.254.3.175:20880] (1/1) from the registry 192.168.110.130:2181 on the consumer 10.254.3.175 using the dubbo version 2.5.3. Last error is: Failed to invoke remote method: getItemById, provider: dubbo://10.254.3.175:20880/com.yapengren.e3mall.service.ItemService?anyhost=true&application=e3-manager-web&check=false&dubbo=2.5.3&interface=com.yapengren.e3mall.service.ItemService&methods=getItemById&pid=108&revision=1.0-SNAPSHOT&side=consumer&timestamp=1520136387466, cause: Failed to send response: Response [id=2, version=2.0.0, status=20, event=false, error=null, result=RpcResult [result=com.yapengren.e3mall.pojo.TbItem@7c1488bf, exception=null]], cause: java.lang.IllegalStateException: Serialized class com.yapengren.e3mall.pojo.TbItem must implement java.io.Serializable
java.lang.IllegalStateException: Serialized class com.yapengren.e3mall.pojo.TbItem must implement java.io.Serializable
```
解决办法：实现 Serializable 序列化接口

3. PageHelper 分页插件版本改为 5.0.1 配置文件解决方案
```
<plugins>
    <!--配置分页插件-->
    <plugin interceptor="com.github.pagehelper.PageInterceptor">
        <!--设置数据库类型    注：4.0.0 以后版本可以不设置该参数，新版本能自动识别底层数据库-->
        <!--<property name="dialect" value="mysql"/>-->
    </plugin>
</plugins>
```