SpringCloud
微服务框架模板


# 关于Nacos的知识点

Eureka：更像是微服务中的一个模块，独立性不高

Nacos：更像是独立的一个组件，依附于服务器而不是项目，微服务可以通过配置Nacos从而使用其功能，因此有集群的需求 

微服务-->Nginx-->Nacos

### 1、注册中心
- 服务注册和发现
- 负载均衡（默认轮询，可设置权重，但因为没有ribbon，所以需要导入loadbalancer依赖）
- 集群设置（用于负载均衡时选择）
- 环境隔离
注意：配置和服务都要指定对应的namespace
- 对比Eureka

### 2、配置管理
主要用来管理一些需要运行时调整的参数，并不是所有配置都放进去（很多微服务配置可以放在 bootstrap.yml 中）

    配置文件读取顺序：
        - 先读取微服务模块的 bootstrap.yml（其中需要定义 微服务名称、环境、文件后缀名，三者决定了启动时到nacos读哪个配置文件）
        - 按照 bootstrap.yml 的信息去读取nacos中的配置 
        - 加载 微服务模块的 application.yml （bootstrap.yml 中配置过的可以省略）

    配置热更新：
        - 注解 @RefreshScope

    多环境配置共享：
        - userService-dev.yaml 和 userService.yaml 都会被读取，前者只会在开发环境下被读取

    nacos集群搭建：
        - 集群：很多相同功能的服务，主要用于分担压力
        - nacos集群主要作用：
            * 当有很多微服务模块都来请求"服务发现"时，nacos压力大，因此需要有集群
            * 保证数据一致性：搭建Mysql集群
            * Nginx：
                反向代理：统一管理集群（比如三个nacos服务ip、端口都不同，但是通过Nginx可以设定一个统一的入口，然后Nginx负责分发）
                负载均衡：



# 关于Feign的知识点

OpenFeign：通过Restful风格的 Client接口，实现远程调用

### 基本使用：
    1、启动类上添加注解，开启Feign服务：@EnableFeignClients
    2、创建Client接口, 指定服务名称、路径、参数、返回类型
    3、通过自动装配使用即可

### 优化
    1、引入HttpClient连接池, 在配置文件中进行设置
    2、可以将所有Client整合到一个模块，其他模块通过调用该包的接口来实现远程调用（实现Client接口的统一管理）