package tr.yildiz.edu.l1108080.repository.repos;

import android.app.Activity;
import android.text.TextUtils;
import tr.yildiz.edu.l1108080.repository.IRepository;
import tr.yildiz.edu.l1108080.repository.RepoDbFile;
import tr.yildiz.edu.l1108080.repository.models.BaseModel;
import tr.yildiz.edu.l1108080.util.MethodResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by y3 on 30/04/2021 10:41.
 */
public abstract class BaseRepository<T extends BaseModel> implements IRepository<T> {
    protected String repo;
    protected List<T> list;
    protected Activity context;

    public <T extends BaseModel> BaseRepository(Activity context, String repo, BaseModel emptyObject) {
        this.context = context;
        this.repo = repo;
        this.list = RepoDbFile.read(context, repo, emptyObject);
        if (list == null) {
            list = new ArrayList<>();
            flushToFile();
        }
    }

    protected void flushToFile() {
        RepoDbFile.write(context, repo, list);
    }

    @Override
    public T getById(String id) {
        for (T item : list) {
            if (item.id().equalsIgnoreCase(id))
                return item;
        }
        return null;
    }

    @Override
    public MethodResponse create(T data) {
        if (!TextUtils.isEmpty(data.id())) {
            T existing = getById(data.id());
            if (existing != null)
                return new MethodResponse().success(false).msg("Record already registered");
        }

        data.id(UUID.randomUUID().toString());
        list.add(data);
        flushToFile();
        return new MethodResponse().id(data.id()).success(true).msg("Record registered");
    }

    @Override
    public MethodResponse edit(T data) {
        int index = list.indexOf(data);
        if (index < 0)
            return new MethodResponse().success(false).msg("Failed to find record:" + data.id());
        list.set(index, data);
        flushToFile();
        return new MethodResponse().id(data.id()).success(true).msg("Record updated");
    }

    @Override
    public MethodResponse delete(String id) {
        T item = getById(id);
        if (item == null)
            return new MethodResponse().success(false).msg("Failed to find record:" + id);
        list.remove(item);
        flushToFile();
        return new MethodResponse().success(true).msg("Record deleted");
    }


    @Override
    public List<T> getAll() {
        return new ArrayList<>(list);
    }

}
