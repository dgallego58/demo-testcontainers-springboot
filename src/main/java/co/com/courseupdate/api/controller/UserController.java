package co.com.courseupdate.api.controller;

import co.com.courseupdate.data.UserDto;
import co.com.courseupdate.service.UserService;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user")
    @Timed(description = "Users metrics", value = "http.server.requests.users"
            , extraTags = {"cardinality", "simple",
            "format", "JSON",
            "info", "user-keyword",
            "presentation", "report",
    })
    public ResponseEntity<Optional<UserDto>> getUserByNameFiltering(@RequestParam String keyword) {
        return ResponseEntity.ok(userService.getUserContainingKeyword(keyword));
    }

    @GetMapping(path = "/user/detail/{id}")
    @Timed( description = "brings any user by match in its id", value = "http.server.requests.users"
            , extraTags = {"cardinality", "simple",
            "format", "JSON",
            "info", "user-id",
            "presentation", "report",
    })
    public ResponseEntity<Optional<UserDto>> getOneById(@PathVariable long id) {
        return ResponseEntity.ok(userService.findOneById(id));
    }

    @GetMapping(path = "/users")
    @Timed(description = "users", value = "http.server.requests.users"
            , extraTags = {"cardinality", "multiple",
            "format", "JSON",
            "info", "users",
            "presentation", "report",
    })
    public ResponseEntity<Iterable<UserDto>> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

}
