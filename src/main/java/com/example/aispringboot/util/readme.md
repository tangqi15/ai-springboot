公共工具类

提取token 可以分为 8步

//  1、提取jwt token
//  2、验证 JWT token 并获取用户信息
//  3、 查询用户信息 验证用户的状态
//  4、 创建 Spring Security 认证对象
//  5、创建 UsernamePasswordAuthenticationToken 对象
//  6、 设置认证信息到 Spring Security 上下文
//  7、 将token 存储到 请求的属性中
//  8、


ResponseUtil 用于处理过滤器中的响应。 为什么不用 Result 处理呢？

Result 是controller 中响应处理。
没有进入到controller, 在 Security 安全层的，就需要用到 ResponseUtil 响应。