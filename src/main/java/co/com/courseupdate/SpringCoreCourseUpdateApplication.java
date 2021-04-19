package co.com.courseupdate;

import co.com.courseupdate.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

import java.util.stream.Stream;

@EntityScan(basePackageClasses = {User.class})

@SpringBootApplication
public class SpringCoreCourseUpdateApplication {

    private static final Logger LOG = LoggerFactory.getLogger(SpringCoreCourseUpdateApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringCoreCourseUpdateApplication.class, args);
    }

    @Bean
    @Order(1)
    CommandLineRunner onStartup() {
        return args -> LOG.info("After init context");
    }

    @Bean
    @Order(2)
    CommandLineRunner afterProfileSet(ConfigurableApplicationContext context) {
        return args -> {
            LOG.info("Showing up profiles");
            Stream.of(context.getEnvironment().getActiveProfiles()).forEach(p -> LOG.info("Profiles are {}", p));
        };
    }

}
