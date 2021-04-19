package co.com.courseupdate.repo;

import co.com.courseupdate.factory.UserTestFactory;
import co.com.courseupdate.models.DocType;
import co.com.courseupdate.models.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@DataJpaTest
@Sql({"classpath:/sql/h2/schema-h2.sql", "classpath:/sql/h2/import-h2.sql"})
class UserRepositoryUnitTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    UserRepository userRepository;

    @Test
    void getUserContainingKeywordShouldBeOk() {

        User user = UserTestFactory.createUser();
        testEntityManager.persistAndFlush(user);
        Optional<User> userName = userRepository.findByNameContaining("David")
                .stream()
                .peek(u -> log.info("Element in BD is: {}", u.toString()))
                .findFirst();
        assertTrue(userName.isPresent());
        assertEquals("David Alejandro", userName.get().getName());
        assertEquals(26, userName.get().getAge());
    }

}