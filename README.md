# springboot-elasticsearch

![Java](https://shields.io/badge/java-8-lightgrey?logo=java&style=for-the-badge) ![Elastic Search](https://shields.io/badge/elasticsearch-7.6.1-lightblue?logo=elasticsearch&style=for-the-badge) ![Spring Boot](https://shields.io/badge/spring%20boot-2.3.2.RELEASE-lightgreen?logo=spring&style=for-the-badge)



## 项目运行

```shell
# 安装依赖
$ npm install
# 前端运行
$ npm run serve
# 安装依赖
$ mvn install 
# 打包
$ mvn package
# 后端运行
$ cd target/
$ java -jar springboot-elasticsearch-1.0-SNAPSHOT.jar
```



## 项目结构

```
├───src
    ├───main
        ├───java 后端代码
        │   └───top
        │       └───parak
        │           ├───aspect     切面层
        │           ├───config     配置层
        │           ├───controller 控制层
        │           ├───entity     实体层
        │           ├───exception  异常层
        │           ├───service    业务层
        │           └───util       工具层
        └───resources
            └───vue 前端代码
                ├───public
                └───src
                    └───assets
                        ├───css
                        └───images
```

