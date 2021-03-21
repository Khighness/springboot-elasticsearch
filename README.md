# springboot-elasticsearch



## 项目运行

```shell
# 前端运行
npm install
npm run serve
# 后端运行
mvn install 
cd target/
java -jar springboot-elasticsearch-1.0-SNAPSHOT.jar
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

