package tr.yildiz.edu.l1108080.repository;

import tr.yildiz.edu.l1108080.repository.models.BaseModel;
import tr.yildiz.edu.l1108080.util.MethodResponse;

import java.util.List;

/**
 * Created by y3 on 30/04/2021 09:13.
 */
public interface IRepository<T extends BaseModel> {
    MethodResponse create(T data);

    MethodResponse edit(T data);

    MethodResponse delete(String id);

    T getById(String id);

    List<T> getAll();
}
