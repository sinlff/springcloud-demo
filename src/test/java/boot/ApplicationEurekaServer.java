package boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@Slf4j
@EnableEurekaServer
@SpringBootApplication(
        exclude = {
        }
)
@ComponentScan(
        basePackages = {
                "com.sinlff.server"
        },
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = {
                    "boot.ApplicationEurekaClient",
                    "boot.ApplicationEurekaServer",
                    "boot.ApplicationMapperTest",
                    "boot.ApplicationTest"
                })
        }
)
public class ApplicationEurekaServer {

    public static void main(String[] args){
        System.setProperty("spring.profiles.active=local2", "local2");
        SpringApplication.run(ApplicationEurekaServer.class, args);
        log.info("EnableEurekaServer启动完成");
    }
}