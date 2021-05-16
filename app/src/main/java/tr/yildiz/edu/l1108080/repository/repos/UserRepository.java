package tr.yildiz.edu.l1108080.repository.repos;

import android.app.Activity;
import tr.yildiz.edu.l1108080.repository.models.User;
import tr.yildiz.edu.l1108080.util.Constants;
import tr.yildiz.edu.l1108080.util.MethodResponse;

/**
 * Created by y3 on 30/04/2021 09:16.
 */
public class UserRepository extends BaseRepository<User> {
    public UserRepository(Activity context) {
        super(context, Constants.RepositoryNameSpace.User, new User());
    }

    public User getByMail(String mail) {
        for (User item : list) {
            if (item.email().equalsIgnoreCase(mail))
                return item;
        }
        return null;
    }

    @Override
    public MethodResponse create(User data) {
        User existing = getByMail(data.email());
        if (existing != null)
            return new MethodResponse().success(false).msg("Email already registered");
        return super.create(data);
    }

}
