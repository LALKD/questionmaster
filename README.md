基于Spring Boot + Redis + Mybatis-plus + Elasticsearch的刷题平台。管理员可以创建题库并批量关联题目；
用户可以分词检索题目、在线刷题并查看刷题记录日历等。

自主搭建 ES 代替 MySQL 模糊查询，并通过为索引绑定 ik 分词器实现了更灵活的分词搜索。

使用 Spring Scheduler 定时同步近期发生更新的 MySQL 题目到 ES，并通过唯一 id 保证每条数据同步的准确性。

刷题记录：基于 Redis BitMap + Redisson 实现用户年度刷题记录的统计，相比数据库存储节约几百倍空间。并通过本地
缓存 + 返回值优化 + 位运算进一步提升接口性能。

使用 Caffeine 本地缓存提升题库查询性能，并通过接入 Hotkey 并配置热 key 探测规则来自动缓存热门题目，防止瞬时流
量击垮数据库。

为防止账号共享，通过 UserAgent 识别用户设备，并基于 Sa-Token 快速实现同端登录冲突检测。

使用 Knife4j + Swagger 自动生成后端接口文档，并通过编写 ApiOperation 等注解补充接口注释，避免了人工编写维护
文档的麻烦。