package boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@Slf4j
@SpringBootApplication(
        exclude = {
        }
)
@ComponentScan(
        basePackages = {
                "com.sinlff.server.mapper",
                "com.sinlff.server.config"
        },
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = {
                    "com.sinlff.server.config.MyMetaObjectHandler"
                })
        }
)
public class ApplicationMapperTest {

    public static void main(String[] args){
        SpringApplication.run(ApplicationMapperTest.class, args);
        log.info("ApplicationTest启动完成");
    }
}