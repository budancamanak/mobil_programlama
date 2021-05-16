package tr.yildiz.edu.l1108080.repository.repos;

import android.app.Activity;
import tr.yildiz.edu.l1108080.repository.models.QuestionOption;
import tr.yildiz.edu.l1108080.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by y3 on 16/05/2021 21:28.
 */
public class QuestionOptionRepository extends BaseRepository<QuestionOption> {
    public QuestionOptionRepository(Activity context) {
        super(context, Constants.RepositoryNameSpace.QuestionOption, new QuestionOption());
    }

    public List<QuestionOption> getByQuestionId(String qid) {
        List<QuestionOption> items = new ArrayList<>();
        for (QuestionOption i : getAll()) {
            if (i.id().equalsIgnoreCase(qid))
                items.add(i);
        }
        return items;
    }
}
