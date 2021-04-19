package co.com.courseupdate.repo;

import co.com.courseupdate.factory.UserTestFactory;
import co.com.courseupdate.models.DocType;
import co.com.courseupdate.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest(properties = {"spring.jpa.hibernate.ddl-auto=none"})
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTestContainerIntTest {

    @Container
    static PostgreSQLContainer database = new PostgreSQLContainer("postgres:13")
            .withDatabaseName("springboot")
            .withUsername("springboot")
            .withPassword("springboot");
    @Autowired
    UserRepository userRepository;

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.url", database::getJdbcUrl);
        propertyRegistry.add("spring.datasource.username", database::getUsername);
        propertyRegistry.add("spring.datasource.password", database::getPassword);
    }

    @Test
    @Sql("classpath:scripts/test_ddl.sql")
    void testShouldStoreAndReturnShouldBeOk() {
        User expected = UserTestFactory.createUser();
        userRepository.save(expected);
        List<User> users = userRepository.findByNameContaining("avid");
        assertFalse(users.isEmpty());
    }

}
