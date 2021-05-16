package tr.yildiz.edu.l1108080.repository.models;

import androidx.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by y3 on 30/04/2021 10:46.
 */

public abstract class BaseModel {
    @Getter
    @Setter
    @Accessors(fluent = true)
    protected String id;

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof BaseModel))
            return false;
        return id.equalsIgnoreCase(((BaseModel) obj).id);
    }
}
