package tr.yildiz.edu.l1108080.repository.models;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by y3 on 30/04/2021 10:38.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Accessors(fluent = true)
@ToString
public class Question extends BaseModel {
    private String text;
    private List<String> attachments;

}
