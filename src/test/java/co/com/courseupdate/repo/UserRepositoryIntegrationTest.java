package co.com.courseupdate.repo;

import co.com.courseupdate.models.DocType;
import co.com.courseupdate.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
                properties = {"spring.jpa.properties.hibernate.format_sql=true",
                        "spring.jpa.show-sql=true"})
@AutoConfigureTestDatabase
@Transactional
class UserRepositoryIntegrationTest {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        List<User> usersData = new ArrayList<>();

        IntStream.range(0, 10).forEach(i -> {
            User user = User.create()
                    .setDni("test" + i)
                    .setDocType(DocType.CC)
                    .setLastName("TestLastName" + i)
                    .setName("TestName" + i);
            usersData.add(user);
        });
        userRepository.saveAll(usersData);
    }

    @TestFactory
    Stream<DynamicTest> userShouldBe10() {
        return Stream.of("Name", "Test", "TestName", "3").map(keyword -> {
            String testDisplay = "CheckingOut names containing keyword ".concat(keyword);
            return dynamicTest(testDisplay, () -> {
                List<User> usersResult = userRepository.findByNameContaining(keyword);
                int resultSize = usersResult.size();
                assertTrue(resultSize >= 1);
                assertThat(usersResult.stream().findFirst().get()).isInstanceOf(User.class);
                assertThat(usersResult).map(User::getDocType).contains(DocType.CC);
            });
        });

    }
}