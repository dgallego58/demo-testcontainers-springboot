package co.com.courseupdate.api.controller;

import co.com.courseupdate.data.UserDto;
import co.com.courseupdate.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.*;

import java.net.URI;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@AutoConfigureWebClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
                properties = {"spring.jpa.properties.hibernate.format_sql=true",
                        "spring.jpa.show-sql=true"})
class UserControllerIntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate testRestTemplate;

    @MockBean
    UserService userService;

    @Test
    void getUserByNameFilteringShouldBeOk() {
        when(userService.getUserContainingKeyword("David")).thenReturn(Optional.of(new UserDto().setName("David")));
        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/user")
                .queryParam("keyword", "David")
                .build()
                .toUri();
        ResponseEntity<UserDto> result = testRestTemplate.getForEntity(uri, UserDto.class);
        UserDto actual = result.getBody();
        assertNotNull(actual);
        assertEquals("David", actual.getName());
    }

}