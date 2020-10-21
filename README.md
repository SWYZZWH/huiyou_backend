# README

### Update 2020/10/20 by zwh

更新了API文档 https://www.showdoc.cc/1096444849681105

已配置https

域名已备案

新增了看板功能



### Update 2020/10/14 by zwh

更新了Todo list

推荐链的初步构思已加到/docs/后端开发文档中



### Update 2020/10/11 by zwh

实现了自动部署



### Update 2020/10/10 by zwh

已将我们的服务设置为守护进程，现在关闭SSH连接后服务仍可以在后台运行

更新了Todo list, 新增任务：管理前端的静态文件（通过Ngnix）

将api的格式改为了ip:port/api/records/...，这是为了和传输文件的请求相区分

测试github的自动化配置工具Action







### 配置java开发环境

- 安装jdk-15，并正确设置环境变量

- 安装eclipse，并配置maven配置文件（maven换源）

- 这一部分可以参考廖雪峰的Java教程

  

### 连接MongoDB

- 官网注册+开通云数据库（已经开通了云数据库，只需要用连接字连接即可，连接字在application.properties文件中）
- 下载Mongo Compass，可视化管理数据库
- 填写与java app的连接字，连接到数据库



### ~~框架下载（框架已弃用）~~

- ~~clone框架 https://www.mongodb.com/blog/post/rest-apis-with-java-spring-boot-and-mongodb~~
- ~~配置application.properties，填入连接字~~



### 项目说明

请先阅读Spring Boot官网教程

https://spring.io/guides/gs/accessing-mongodb-data-rest/

项目的整体示意图见下，结合项目代码中的文档注释即可了解项目

![image-20201004200923459](https://github.com/SWYZZWH/huiyou_backend/blob/master/pics/image-20201004200923459.png)



### 运行项目

git clone git@github.com:SWYZZWH/huiyou_backend.git

eclipse->open file from filesystem导入项目

package explorer视图中右键项目主目录， run as ... > maven clean 

run as ... > maven build ... goals 设为 package，build 项目

找到生成的target文件夹，在该文件夹下启动命令行运行项目 java -jar demo-0.0.1-SNAPSHOT.jar



### API文档

https://www.showdoc.cc/1096444849681105



### 测试项目

使用postman测试

| 请求类型 | URL                                                          | 备注                                           |
| -------- | ------------------------------------------------------------ | ---------------------------------------------- |
| GET      | https://huiyou.fun/api/records                               | 返回全部历史记录                               |
| GET      | https://huiyou.fun/api/records?bv=BV1kt4y1q7GE               | 根据Bv号查询历史记录                           |
| GET      | https://huiyou.fun/api/records?uid=bili_5249176387           | 根据uid查询历史记录                            |
| GET      | https://huiyou.fun/api/records?bv=BV1kt4y1q7GE&uid=bili_5249176387 | 根据uid和bv号查询历史记录                      |
| POST     | https://huiyou.fun/api/records/                              | 新增一条历史记录，需要设置Header和Body（见下） |

 header中新增key-value对：Content-Type     application/json

Body选择Raw，格式如下：

```json
{
  "uid" : "bili_5249176387",
  "bv" : "BV1kt4y1q7GE"
}
```

其中bv号和uid都可以建议修改，以达到测试的目的





### 部署

- 准备工具xftp,xshell(腾讯云自带的不好用)，并分别与服务器建立连接

- 服务器安装jdk+maven，通过yum安装很方便

  ```
  wget --no-check-certificate -c --header "Cookie: oraclelicense=accept-securebackup-cookie" https://download.oracle.com/otn-pub/java/jdk/15+36/779bf45e88a44cbd9ea6621d33e33db1/jdk-15_linux-x64_bin.rpm
  sudo yum -y install dnf
  sudo dnf localinstall jdk-15_linux-x64_bin.rpm
  sudo yum -y install maven
  ```

- rest服务的端口号在application.properties文件中配置：

  server.port=8080

- 本地使用maven clean + maven build（package）生成target目录与jar文件

- 使用xftp将jar文件传到server的/usr/local/huiyou-backend目录下（别的目录也可）
- 在该目录下 输入命令：java -jar ***.jar 启动服务
- 现在可以在任何位置以http://106.54.69.78:8080/访问服务

参考：

https://zhuanlan.zhihu.com/p/58388786 部署

https://zhuanlan.zhihu.com/p/133131988 部署

https://www.osradar.com/how-to-install-java-15-on-centos-8-centos-7/ 安装jdk-15



### 测试部署

- 使用postman并输入如下url作为测试（GET请求）：

  http://106.54.69.78:8080/api/records/byUid/bili_5249176387

  应当能看到类似结果：

  **body**

  ```json
  [
      {
          "uid": "bili_5249176387",
          "bv": "BV1WZ4y1577e"
      },
      {
          "uid": "bili_5249176387",
          "bv": "BV1kt4y1q7GE"
      }
  ]
  ```



### **Bugs**

- “Source folder is not a Java project” error in eclipse

  Right click your project > Maven > Update Project



### Todo list

- [x] 部署到服务器
- [x] 将服务注册为守护进程（避免关掉SSH连接进程就结束）
- [x] 自动部署
- [ ] 新增字段（播放时间，缩略图，作者等，需要和前端沟通）
- [ ] 为每个用户可存储的记录数设置上限
- [ ] 配置域名并备案
- [ ] 改造github项目，添加看板功能，规范协作流程
- [ ] 思考如何实现推荐链




### 一些想法

**视频筛选规则/标准**

- 视频长度

- 清晰度

- 有无字幕

- 非手机画幅

- 无水印

- 严肃向分区
