package tr.yildiz.edu.l1108080.repository.models;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * Created by y3 on 30/04/2021 09:09.
 */

@Getter
@Setter
@Accessors(fluent = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User extends BaseModel {
    private String email;
    private String name;
    private String surname;
    private String password;
    private String phone;
}
