# ShardingSphere实现分库 + 读写分离

该项目拉下后配置数据库地址，和创建相应的库和表后就可以运行成功。

```makefile
spring-boot-sharding-sphere # 父工程
 
db-read-write  #实现读写分离功能
sub-db-table  #实现分库分表功能
sub-db-table-read-write   #实现分库分表 + 读写分离

sub-table #实现分表功能
sub-table-read-write  #实现分表 + 读写分离
```

db-read-write  #实现读写分离功能

![image](https://github.com/webVueBlog/spring-boot-sharding-sphere/assets/59645426/243aef7c-c6c8-43a3-9476-ecf90642c304)

![image](https://github.com/webVueBlog/spring-boot-sharding-sphere/assets/59645426/c308db0d-ef1b-4ba2-bac5-3bf8c9941e28)

<img width="850" alt="image" src="https://github.com/webVueBlog/spring-boot-sharding-sphere/assets/59645426/3d309a89-5eb1-4202-b138-6ecb3c4bc2a9">

<img width="833" alt="image" src="https://github.com/webVueBlog/spring-boot-sharding-sphere/assets/59645426/a4ebb049-a44e-4484-92f7-0b50860e2e06">


sub-db-table  #实现分库分表功能

<img width="807" alt="image" src="https://github.com/webVueBlog/spring-boot-sharding-sphere/assets/59645426/ed48ab66-3ebc-4931-95f5-cfdfa40b3b7b">

<img width="843" alt="image" src="https://github.com/webVueBlog/spring-boot-sharding-sphere/assets/59645426/4f3786cd-fc63-4d1c-8af1-88a31d6248b4">

sub-db-table-read-write   #实现分库分表 + 读写分离

sub-table #实现分表功能

sub-table-read-write  #实现分表 + 读写分离
