### 一.简介

#### 1.1 关键词

* 测试后门工具
* 后台测试工具
* 在线测试方法
* 在线测试工具

#### 1.2 核心原理

* 反射原理
* 方法参数名解析

#### 1.3 技术实现

* java反射原理
* Spring反射调用
* 方法参数名解析
* REST服务调用
* 测试后门权限管控

### 二.module设计

#### 2.1 核心层core

> **maven依赖**

```xml
<dependency>
    <groupId>com.wunong.smart</groupId>
    <artifactId>smart-reflect-invoker-core</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

> 功能介绍

* 定义核心api
* 定义api基础实现

#### 2.2 quick starter

> maven依赖

```xml
<dependency>
    <groupId>com.wunong.smart</groupId>
    <artifactId>smart-reflect-invoker-starter</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

> **功能介绍**

* 定义核心api注册，与Spring结合，快速使用；
* 后门工具请求拦截与权限管控；
* REST api接口请求调用；

### 三.基础实现

#### 3.1 核心依赖

> Spring工具

* Spring工具类AopUtils.getTargetClass，获取代理对象对应的目标对象；
* 方法名参数解析：ParameterNameDiscover，对应实现类为DefaultParameterNameDiscoverer，用于解析出声明方法的参数名，用于反射调用；

#### 3.2 反射调用

> **方法调用**

* 反射调用static方法，入口方法：DefaultReflectInvoker
* 反射调用Spring bean，入口方法：SpringReflectInvoker
* jdk反射调用方法，入口方法：DefaultReflectInvoker

#### 3.3 快速注册

> 快速启动

* 引入starter
* 使用@Component注册扫描的包路径，对应包名如下；

```java
@Configuration
public class RestInvokeConfiguration {
    /**
     * 包名称
     */
    public static final String PACKAGE = "com.wunong.smart.invoker";
}
```

> 权限设置

* 接口抽象：InvokeAuthentication，默认实现类DefaultInvokeAuthentication，支持按照环境变量进行开启或关闭使用；
* 支持自定义实现，使用Spring Bean的注册方式注册即可；

### 四.入口调用

#### 4.1 入口参数

> 基础入口参数

```java
@Data
public class InvokeParam implements Serializable {
    /**
     * 类标识，如spring bean标识、全类名
     */
    private String className;

    /**
     * 对应的方法名
     */
    private String methodName;

    /**
     * 调用参数，支持json格式
     */
    private Map<String, Object> params;

    /**
     * 参数类型列表，解决重名方法问题
     */
    private List<String> paramTypes;
}
```

> 用户认证参数

```java
@Getter
@Setter
@ToString(callSuper = true)
public class AuthInvokeParam extends InvokeParam {
    /**
     * 操作人
     */
    private User user;
}
```

#### 4.2 jdk反射调用

> 反射调用适用场景

* 调用static方法；
* 调用java实例方法，实例通过java反射调用获取，需要有默认构造方法；

> REST URL

* 请求url： http:localhost:8080/{项目配置路径}/backdoor/default.invoke
* 请求数据：JSON格式

#### 4.3 spring反射调用

> spring调用适用场景

* Spring web应用或Spring Boot应用下调用Spring Bean方法；
* junit web测试调用Spring Bean方法；

> REST URL

* 请求url： http:localhost:8080/{项目配置路径}/backdoor/spring.invoke
* 请求数据：JSON格式

#### 4.4 参数举例
> Spring Bean反射调用参数
```json
{
  "className": "SpringBean名称",
  "methodName": "方法名称",
  "params": {
    "key": "value"
  },
  "paramTypes": ["String"],
  "user": {
    "id": 0,
    "role": 1,
    "name": "系统"
  }
}
```

> 普通反射调用参数
```json
{
  "className": "java全类名",
  "methodName": "方法名称",
  "params": {
    "key": "value"
  },
  "paramTypes": ["String"],
  "user": {
    "id": 0,
    "role": 1,
    "name": "系统"
  }
}
```