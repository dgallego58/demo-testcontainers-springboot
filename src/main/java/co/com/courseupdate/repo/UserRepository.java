package co.com.courseupdate.repo;

import co.com.courseupdate.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByNameContaining(String keyword);

}
