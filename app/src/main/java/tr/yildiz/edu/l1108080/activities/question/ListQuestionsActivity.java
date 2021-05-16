package tr.yildiz.edu.l1108080.activities.question;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import tr.yildiz.edu.l1108080.R;
import tr.yildiz.edu.l1108080.repository.IRepository;
import tr.yildiz.edu.l1108080.repository.RepositoryService;
import tr.yildiz.edu.l1108080.repository.models.Question;
import tr.yildiz.edu.l1108080.repository.models.QuestionOption;
import tr.yildiz.edu.l1108080.util.Constants;
import tr.yildiz.edu.l1108080.util.MethodResponse;

public class ListQuestionsActivity extends AppCompatActivity implements QuestionAdapter.QuestionLongClickListener {

    RecyclerView listQuestions;
    QuestionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_questions);

        listQuestions = findViewById(R.id.listQuestions);
        IRepository<Question> repository = RepositoryService.get().getRepository(this, Constants.RepositoryNameSpace.Question);
        IRepository<QuestionOption> qrep = RepositoryService.get().getRepository(this, Constants.RepositoryNameSpace.QuestionOption);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listQuestions.setLayoutManager(layoutManager);
        adapter = new QuestionAdapter(this, repository.getAll(), qrep.getAll(), this);
        listQuestions.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(listQuestions.getContext(),
                                                                                layoutManager.getOrientation());
        listQuestions.addItemDecoration(dividerItemDecoration);
    }


    @Override
    public void clicked(Question item) {
        Toast.makeText(this, item.text(), Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(item.text());
        builder.setIcon(R.drawable.ic_launcher_foreground);
        builder.setMessage("What would you like to do?");
        builder.setPositiveButton("Cancel",
                                  new DialogInterface.OnClickListener() {
                                      public void onClick(DialogInterface dialog, int id) {
                                          dialog.cancel();
                                      }
                                  });

        builder.setNeutralButton("Edit",
                                 new DialogInterface.OnClickListener() {
                                     public void onClick(DialogInterface dialog, int id) {
                                         dialog.cancel();
                                         editItem(item);
                                     }
                                 });

        builder.setNegativeButton("Delete",
                                  new DialogInterface.OnClickListener() {
                                      public void onClick(DialogInterface dialog, int id) {
                                          deleteItem(item);
                                          dialog.cancel();
                                      }
                                  });
        builder.create().show();
    }

    private void editItem(Question item) {
        Intent intent = new Intent(getApplicationContext(), AddQuestionActivity.class);
        intent.putExtra("Question.Id", item.id());
        startActivity(intent);
    }

    private void deleteItem(Question item) {
        IRepository<Question> repository = RepositoryService.get().getRepository(this, Constants.RepositoryNameSpace.Question);
        IRepository<QuestionOption> qrep = RepositoryService.get().getRepository(this, Constants.RepositoryNameSpace.QuestionOption);

        MethodResponse mr = repository.delete(item.id());
        if (mr.success()) {
            adapter.setData(repository.getAll(), qrep.getAll());
        }
        Toast.makeText(this, mr.msg(), Toast.LENGTH_SHORT).show();


    }
}