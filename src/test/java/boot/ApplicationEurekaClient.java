package boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@Slf4j
@EnableEurekaClient
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
public class ApplicationEurekaClient {

    public static void main(String[] args){
        SpringApplication.run(ApplicationEurekaClient.class, args);
        log.info("ApplicationEurekaClient启动完成");
    }
}