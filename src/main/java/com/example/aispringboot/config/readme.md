

原来的  localhost:1236/api/test 访问可以得到一串json 数据

引入meaven 包

 <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

之后 再次访问  localhost:1236/api/test 
会 重定向 到 http://localhost:1236/login 一个默认的登录页面


我们需要修改配置信息  创建 config 包
创建 SecurityConfig 类

