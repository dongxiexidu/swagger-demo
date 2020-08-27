package com.etrans.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;


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
