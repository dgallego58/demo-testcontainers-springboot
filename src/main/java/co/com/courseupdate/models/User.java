package co.com.courseupdate.models;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Accessors(chain = true)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "users", schema = "demo")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(allocationSize = 10, name = "user_seq", schema = "demo"  )
    private Long id;
    private String name;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private DocType docType;
    private String dni;
    private int age;
    private String career;

    public static User create() {
        return new User();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        User user = (User) o;

        return id != null && id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return 562048007;
    }
}
