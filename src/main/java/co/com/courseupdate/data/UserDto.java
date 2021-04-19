package co.com.courseupdate.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@NoArgsConstructor
public class UserDto {

    private String name;
    private String lastName;
    private String docType;
    private String dni;

}
