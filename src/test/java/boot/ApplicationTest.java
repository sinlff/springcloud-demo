package boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@Slf4j
//@EnableEurekaServer
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
                    "com.sinlff.server.config.MyMetaObjectHandler"
                })
        }
)
public class ApplicationTest {

    public static void main(String[] args){
        SpringApplication.run(ApplicationTest.class, args);
        log.info("ApplicationTest启动完成");
    }
}