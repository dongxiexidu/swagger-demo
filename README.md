# swagger-demo
 spring swagger learn

### 1.依赖
```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.9.2</version>
</dependency>
<!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger-ui -->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.9.2</version>
</dependency>
```
### 2.config
```java
@Configuration // 表明他是一个配置类
@EnableSwagger2
public class SwaggerConfig {


    // http://localhost:8080/swagger-ui.html  注意端口号是否是8080

    // 如何进行分组?
    // 只需要配置多个docket
    @Bean
    public Docket docket1() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("A");
    }

    @Bean
    public Docket docket2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("B")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.etrans.otherController"))
                .build();
    }


    // 配置swagger的Docket的bean实例
    @Bean
    public Docket docket(Environment environment) {

        // 注意: 在正式发布的时候,关闭swagger,1.出于安全考虑 2.节省运行内存
        // 设置要显示的swagger环境
        Profiles profiles = Profiles.of("dev","test");
        boolean isDevOrTest = environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("船长")
                .enable(isDevOrTest)// 是否启动swagger,
                .select()
                // 配置扫描接口的方式
                .apis(RequestHandlerSelectors.basePackage("com.etrans"))
                // 过滤什么路径
                //.paths(PathSelectors.ant("/otherController/**"))
                .build();
    }

    private ApiInfo apiInfo() {
        // 作者信息
        Contact contact = new Contact("船长","https://www.jianshu.com/u/6f76b136c31e", "863223764@qq.com");

        return new ApiInfo(
                "船长Api Documentation",
                "学如逆水行舟,不进则退",
                "1.0",
                "https://dongxiexidu.github.io/tags.html",
                 contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList()
        );
    }

}
```

### 3.Api文档注释
```java
@ApiModel("用户实体类")
public class User {

    @ApiModelProperty("用户名")
    public String username;
    @ApiModelProperty("密码")
    public String password;
}
```

### 4.controller示例
```java
@RestController
public class HelloController {

    //@RequestMapping("/hello")
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    // 只要我们的接口中,返回值中存在实体类,它就会被扫描到swagger中
    @PostMapping("/user")
    public User user() {
        return new User();
    }

    @ApiOperation("hello控制类")
    @GetMapping("/hello2")
    public String hell2(@ApiParam("用户名") String username) {
        return "hello" + username;
    }

    @ApiOperation("post测试类")
    @PostMapping("/post")
    public User post(@ApiParam("用户名") User user) {
        return user;
    }
}
```

### 总结:

- 1.如何进行分组? 只需要配置多个docket
- 2.文档接口地址:  http://localhost:8080/swagger-ui.html
- 3.如何给指定包生成接口文档?
- 4.生产环境关闭Swagger,开发或者测试环境开启Swagger,如何做?
- 5.如何给model中字段添加注释?
