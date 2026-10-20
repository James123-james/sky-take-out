# 苍穹外卖（sky-take-out）

一套中餐在线订餐系统，采用 Spring Boot 单体架构，包含管理端与用户端两端：
管理端供员工进行员工/分类/菜品/套餐管理、订单处理、数据统计与报表导出；
用户端为微信小程序（mp-weixin），支持微信登录、浏览菜品套餐、购物车、下单与微信支付。

## 技术栈（按 pom.xml 实录）

| 分类      | 技术                                                         | 版本          |
| --------- | ------------------------------------------------------------ | ------------- |
| 基础框架  | Spring Boot（父 POM）                                        | 2.7.3         |
| ORM       | mybatis-spring-boot-starter                                  | 2.2.0         |
| 分页      | pagehelper-spring-boot-starter                               | 1.3.0         |
| 连接池    | druid-spring-boot-starter                                    | 1.2.1         |
| 数据库    | MySQL（驱动版本由 Boot 父 POM 管理）                         | 8.x           |
| 缓存      | spring-boot-starter-cache + spring-boot-starter-data-redis   | 随 Boot 2.7.3 |
| WebSocket | spring-boot-starter-websocket                                | 随 Boot 2.7.3 |
| 认证      | jjwt                                                         | 0.9.1         |
| 对象存储  | aliyun-sdk-oss                                               | 3.10.2        |
| 微信支付  | wechatpay-apache-httpclient                                  | 0.4.8         |
| 接口文档  | knife4j-spring-boot-starter                                  | 3.0.2         |
| AOP       | aspectjrt / aspectjweaver                                    | 1.9.4         |
| 报表导出  | Apache POI / poi-ooxml                                       | 3.16          |
| 工具库    | fastjson 1.2.76、commons-lang 2.6、lombok 1.18.30、jaxb-api 2.3.1 | —             |

## 系统架构

![架构图](docs/架构图.png)

Maven 三模块：`sky-common`（工具类/常量/配置属性）、`sky-pojo`（entity/DTO/VO）、`sky-server`（业务代码，端口 8080）。

## 模块说明（com.sky 真实包结构）

| 包                     | 说明                                                         |
| ---------------------- | ------------------------------------------------------------ |
| controller.admin       | 管理端接口（/admin/**）：员工、分类、菜品、套餐、店铺状态、订单、报表、工作台、文件上传 |
| controller.user        | 用户端接口（/user/**）：登录、店铺状态、分类、菜品、套餐、地址簿、购物车、订单 |
| controller.notify      | 微信支付回调接口（/notify/paySuccess）                       |
| service / service.impl | 业务层，接口与实现分离                                       |
| mapper                 | 数据访问层：MyBatis 接口 + XML（resources/mapper，11 个）+ @Select 注解并存 |
| interceptor            | 管理端/用户端两个 JWT 拦截器                                 |
| config                 | WebMvc（拦截器注册/接口文档）、Redis、OSS、WebSocket 配置    |
| annotation / aspect    | @AutoFill 注解 + AOP 自动填充 create_time/update_user 等公共字段 |
| task                   | Spring Task 定时任务（超时订单处理、WebSocket 心跳推送）     |
| websocket              | WebSocketServer，来单提醒与催单推送（/ws/{sid}）             |
| handler                | 全局异常处理器                                               |

## 核心表

![ER图](docs/ER图.png)

共 11 张表：employee、category、dish、dish_flavor、setmeal、setmeal_dish、user、address_book、shopping_cart、orders、order_detail（无显式外键约束，均为逻辑关联）。

## 运行方式

1. 创建 MySQL 数据库 `sky_take_out`，导入 `sql/_localhost__5_-2026_10_19_20_23_39-dump.sql`；
2. 修改 `sky-server/src/main/resources/application-dev.yml`：MySQL 账号密码、Redis、阿里云 OSS、微信 appid/secret 及支付证书路径（本地调试可保持 `sky.pay.mock: true` 启用模拟支付）；
3. 启动 MySQL 与 Redis（默认 localhost:6379，database 10），运行 `SkyApplication`，服务端口 8080，接口文档 `http://localhost:8080/doc.html`；
4. 将 `mp-weixin` 导入微信开发者工具（baseUrl 默认 http://localhost:8080）；管理端前端可置于 nginx 的 html 目录，由 `/api/` 反向代理到 `http://localhost:8080/admin/`。