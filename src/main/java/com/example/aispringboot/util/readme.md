公共工具类

提取token 可以分为 8步

//  1、提取jwt token
//  2、
//  3、
//  4、
//  5、
//  6、
//  7、
//  8、


ResponseUtil 用于处理过滤器中的响应。 为什么不用 Result 处理呢？

Result 是controller 中响应处理。
没有进入到controller, 在 Security 安全层的，就需要用到 ResponseUtil 响应。