package tr.yildiz.edu.l1108080.repository.repos;

import android.app.Activity;
import android.text.TextUtils;
import tr.yildiz.edu.l1108080.repository.models.Question;
import tr.yildiz.edu.l1108080.util.Constants;
import tr.yildiz.edu.l1108080.util.MethodResponse;

/**
 * Created by y3 on 30/04/2021 10:40.
 */
public class QuestionRepository extends BaseRepository<Question> {

    public QuestionRepository(Activity context) {
        super(context, Constants.RepositoryNameSpace.Question, new Question());
    }

    public Question getByText(String text) {
        for (Question item : list) {
            if (!TextUtils.isEmpty(item.text()) && item.text().equalsIgnoreCase(text))
                return item;
        }
        return null;
    }

    @Override
    public MethodResponse create(Question data) {
        Question existing = getByText(data.text());
        if (existing != null)
            return new MethodResponse().success(false).msg("Question already registered");
        return super.create(data);
    }

}
