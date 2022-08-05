SpringCloud
微服务框架模板

***

# Nacos

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

***

# Feign

OpenFeign：通过Restful风格的 Client接口，实现远程调用

### 基本使用：
    1、启动类上添加注解，开启Feign服务：@EnableFeignClients
    2、创建Client接口, 指定服务名称、路径、参数、返回类型
    3、通过自动装配使用即可

### 优化
    1、引入HttpClient连接池, 在配置文件中进行设置
    2、可以将所有Client整合到一个模块，其他模块通过调用该包的接口来实现远程调用（实现Client接口的统一管理）

***

# Gateway

Gateway：作为微服务的阀门，主要功能有：路由选择、负载均衡、身份验证和权限校验（这一点还需要和单体的SpringSecurity进行对比学习）  
同时网关的使用也和Nacos离不开关系，其路由功能可以直接通过nacos中的服务管理来进行指向

### 基本使用：
    - 主要是在application.yml中的配置：
        1、路由的唯一标识：
            id: user-service
        2、路由的目标地址（nacos服务管理）：
            uri: lb://userService
        3、路由的断言，判断请求路径是否符合
            predicates: 
                - Path=/user/**  

    - 过滤器：路由过滤器、默认过滤器、全局过滤器（自定义）  
  
    - cors跨域设置（application.yml中配置）

# RabbitMQ

消息队列：主要用于异步消息的传递  
- 异步：先把任务发出去，有没有完成是另一回事，完成任务后可以进行回调。
- 同步：必须按顺序执行，顺序执行过程中，一个任务没有完成就会阻塞，如果耗时大，则用户体验较差。
  

- 异步：一般用于对响应要求不高的服务，先将任务进行分发，在后台进行处理。
- 同步：一般用于和用户进行实时交互（Feign）

### RabbitMQ的基本使用：
    1、通过docker镜像安装，将数据文件挂载到本地。打开管理页面需要开启插件
    2、通过使用SpringAMQP来进行消息的接收和发送更加方便，使用对象RabbitTemplate来进行消息的发送
    3、通过设置 @RabbitListener的binding属性，来对消息队列进行监听绑定，其中有三个参数：
        value：队列名称
        exchange：交换机名称
        key：队列的路由标识（Routing key）

### 五种消息模型：
#### 没有交换机：
    1、BasicQueue：
        消息->消息队列->一个消费者
    2、WorkQueue：
        消息->消息队列->多个消费者
        可以在配置文件设置预取值，保证能者多劳
#### 有交换机（发布订阅）：
    1、Fanout Exchange：广播
        交换机将消息发给所有绑定的队列
    2、Direct Exchange：路由
        交换机将消息发送给有对应 Routing key 的队列，实现消息路由
    3、Topic Exchange：主题
        和路由差不多，不过Routing key的值为多段 china.new，用.隔开，可以使用占位符，也是将消息发给符合routing key的队列

### 关于消息的类型：
    按道理可以是任意类型（Object），但是必须要进行序列化，可以自定义序列化方式，自行添加一个消息转换器MessageConverter
    使用@Bean注入容器即可
