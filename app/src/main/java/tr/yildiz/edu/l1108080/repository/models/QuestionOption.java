package tr.yildiz.edu.l1108080.repository.models;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * Created by y3 on 16/05/2021 21:27.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Accessors(fluent = true)
@ToString
public class QuestionOption extends BaseModel {
    private String optionTag;
    private String text;
    private boolean correct;
    private String questionId;
}
