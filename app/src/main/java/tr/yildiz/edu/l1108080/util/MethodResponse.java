package tr.yildiz.edu.l1108080.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by y3 on 30/04/2021 09:13.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Accessors(fluent = true)
public class MethodResponse<T> {
    private boolean success;
    private String msg;
    private String id;
    private T data;


    public MethodResponse(Exception ex) {
        success = false;
        msg = ex.getMessage();
    }
}
