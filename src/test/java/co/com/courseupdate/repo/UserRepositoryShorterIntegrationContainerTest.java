package co.com.courseupdate.repo;

import co.com.courseupdate.factory.UserTestFactory;
import co.com.courseupdate.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(properties = {
        "spring.test.database.replace=NONE",
        "spring.datasource.url=jdbc:tc:postgresql:13:///springboot",
        "spring.jpa.hibernate.ddl-auto=none"
})
class UserRepositoryShorterIntegrationContainerTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @Sql("/scripts/test_ddl.sql")
    void testUserRepositoryShouldBeOk() {
        User expected = UserTestFactory.createUser();
        User actual = userRepository.save(expected);
        assertEquals(actual, expected);
    }

}
