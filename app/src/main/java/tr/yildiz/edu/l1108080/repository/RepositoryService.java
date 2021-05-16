package tr.yildiz.edu.l1108080.repository;

import android.app.Activity;
import tr.yildiz.edu.l1108080.repository.models.BaseModel;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * Created by y3 on 30/04/2021 09:17.
 */
public final class RepositoryService {

    private static RepositoryService instance;
    private HashMap<String, IRepository> list;

    private RepositoryService() {
        list = new HashMap<>();
    }

    public static RepositoryService get() {
        if (instance == null)
            instance = new RepositoryService();
        return instance;
    }

    public <T extends BaseModel> IRepository<T> getRepository(Activity context, String repo) {
        if (list.containsKey(repo))
            return (IRepository<T>) list.get(repo);
        Class<?> clazz = null;
        try {
            clazz = Class.forName(repo);
            IRepository<T> obj = (IRepository<T>) clazz.getDeclaredConstructor(Activity.class).newInstance(context);
            list.put(repo, obj);
            return obj;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
