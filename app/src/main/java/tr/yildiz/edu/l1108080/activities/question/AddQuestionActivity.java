package tr.yildiz.edu.l1108080.activities.question;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import tr.yildiz.edu.l1108080.R;
import tr.yildiz.edu.l1108080.repository.IRepository;
import tr.yildiz.edu.l1108080.repository.RepositoryService;
import tr.yildiz.edu.l1108080.repository.models.Question;
import tr.yildiz.edu.l1108080.repository.models.QuestionOption;
import tr.yildiz.edu.l1108080.repository.repos.QuestionOptionRepository;
import tr.yildiz.edu.l1108080.util.Constants;
import tr.yildiz.edu.l1108080.util.MethodResponse;

import java.util.List;
import java.util.UUID;

public class AddQuestionActivity extends Activity {

    EditText txtQuestion, txtAnsA, txtAnsB, txtAnsC, txtAnsD, txtAnsE;
    CheckBox chkAnsA, chkAnsB, chkAnsC, chkAnsD, chkAnsE;
    Button btnCancel, btnSave, btnMedia;
    LinearLayout cntMedias;
    private Question itemBeingEdited;
    private List<QuestionOption> optionsBeingEdited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        txtQuestion = findViewById(R.id.txtQuestion);
        txtAnsA = findViewById(R.id.txtAnsA);
        txtAnsB = findViewById(R.id.txtAnsB);
        txtAnsC = findViewById(R.id.txtAnsC);
        txtAnsD = findViewById(R.id.txtAnsD);
        txtAnsE = findViewById(R.id.txtAnsE);

        chkAnsA = findViewById(R.id.chkAnsA);
        chkAnsB = findViewById(R.id.chkAnsB);
        chkAnsC = findViewById(R.id.chkAnsC);
        chkAnsD = findViewById(R.id.chkAnsD);
        chkAnsE = findViewById(R.id.chkAnsE);

        CompoundButton.OnCheckedChangeListener clisten = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) return;
                if (buttonView != chkAnsA)
                    chkAnsA.setChecked(false);
                if (buttonView != chkAnsB)
                    chkAnsB.setChecked(false);
                if (buttonView != chkAnsC)
                    chkAnsC.setChecked(false);
                if (buttonView != chkAnsD)
                    chkAnsD.setChecked(false);
                if (buttonView != chkAnsE)
                    chkAnsE.setChecked(false);
            }
        };

        chkAnsA.setOnCheckedChangeListener(clisten);
        chkAnsB.setOnCheckedChangeListener(clisten);
        chkAnsC.setOnCheckedChangeListener(clisten);
        chkAnsD.setOnCheckedChangeListener(clisten);
        chkAnsE.setOnCheckedChangeListener(clisten);


        btnCancel = findViewById(R.id.btnCancel);
        btnMedia = findViewById(R.id.btnAddMedia);
        btnSave = findViewById(R.id.btnSave);
        cntMedias = findViewById(R.id.cntMediasInQuestion);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSave();
            }
        });

        Intent intent = getIntent();
        String qId = intent.getStringExtra("Question.Id");
        if (!TextUtils.isEmpty(qId)) {
            IRepository<Question> repository = RepositoryService.get().getRepository(this, Constants.RepositoryNameSpace.Question);
            IRepository<QuestionOption> _qopRep = RepositoryService.get().getRepository(this, Constants.RepositoryNameSpace.QuestionOption);
            itemBeingEdited = repository.getById(qId);
            if (itemBeingEdited != null) {
//                txtAnswer.setText(itemBeingEdited.answer());
                txtQuestion.setText(itemBeingEdited.text());
            }
            optionsBeingEdited = ((QuestionOptionRepository) (_qopRep)).getByQuestionId(qId);
            if (optionsBeingEdited != null) {
                for (QuestionOption op : optionsBeingEdited) {
                    setUIFromQuestionOptionTag(op.optionTag(), op.text(), op.correct());
                }
            }
        }
    }

    boolean validate() {
        if (TextUtils.isEmpty(txtQuestion.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please provide question", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(txtAnsA.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please provide option A", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(txtAnsB.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please provide option B", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(txtAnsC.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please provide option C", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(txtAnsD.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please provide option D", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(txtAnsE.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please provide option E", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!checkIfAnswerSet()) {
            Toast.makeText(getApplicationContext(), "Please set an answer", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    boolean checkIfAnswerSet() {
        return chkAnsA.isChecked() || chkAnsB.isChecked() || chkAnsC.isChecked() || chkAnsD.isChecked() || chkAnsE.isChecked();
    }

    QuestionOption generateQuestionFromUI(String tag, String questionId) {
        if ("a" == tag)
            return new QuestionOption(tag, txtAnsA.getText().toString(), chkAnsA.isChecked(), questionId);
        if ("b" == tag)
            return new QuestionOption(tag, txtAnsB.getText().toString(), chkAnsB.isChecked(), questionId);
        if ("c" == tag)
            return new QuestionOption(tag, txtAnsC.getText().toString(), chkAnsC.isChecked(), questionId);
        if ("d" == tag)
            return new QuestionOption(tag, txtAnsD.getText().toString(), chkAnsD.isChecked(), questionId);
        if ("e" == tag)
            return new QuestionOption(tag, txtAnsE.getText().toString(), chkAnsE.isChecked(), questionId);
        return null;
    }

    void setUIFromQuestionOptionTag(String tag, String text, boolean correct) {
        if ("a" == tag) {
            txtAnsA.setText(text);
            chkAnsA.setChecked(correct);
        }
        if ("b" == tag) {
            txtAnsB.setText(text);
            chkAnsB.setChecked(correct);
        }
        if ("c" == tag) {
            txtAnsC.setText(text);
            chkAnsC.setChecked(correct);
        }
        if ("d" == tag) {
            txtAnsD.setText(text);
            chkAnsD.setChecked(correct);
        }
        if ("e" == tag) {
            txtAnsE.setText(text);
            chkAnsE.setChecked(correct);
        }
    }

    void doSave() {
        if (!validate())
            return;
        MethodResponse mr;
        if (itemBeingEdited == null) {
            Question item = new Question();
            item.text(txtQuestion.getText().toString());
            item.id(UUID.randomUUID().toString());
            IRepository<Question> repository = RepositoryService.get().getRepository(this, Constants.RepositoryNameSpace.Question);

            mr = repository.create(item);
            if (mr.success()) {
                String qId = mr.id();
                String[] ts = new String[]{
                        "a", "b", "c", "d", "e"
                };
                IRepository<QuestionOption> qrep = RepositoryService.get().getRepository(this, Constants.RepositoryNameSpace.QuestionOption);
                for (String tag : ts) {
                    QuestionOption qop = generateQuestionFromUI(tag, qId);
                    qrep.create(qop);
                }
            } else {
                Toast.makeText(getApplicationContext(), "Fail:" + mr.msg(), Toast.LENGTH_SHORT).show();
            }
        } else {
            IRepository<Question> repository = RepositoryService.get().getRepository(this, Constants.RepositoryNameSpace.Question);

            itemBeingEdited.text(txtQuestion.getText().toString());
            mr = repository.edit(itemBeingEdited);
            if (mr.success()) {
                String[] ts = new String[]{
                        "a", "b", "c", "d", "e"
                };
                IRepository<QuestionOption> qrep = RepositoryService.get().getRepository(this, Constants.RepositoryNameSpace.QuestionOption);
                for (String tag : ts) {
                    QuestionOption qop = generateQuestionFromUI(tag, itemBeingEdited.id());
                    qrep.edit(qop);
                }
            } else {
                Toast.makeText(getApplicationContext(), "Fail:" + mr.msg(), Toast.LENGTH_SHORT).show();
            }
        }
        if (mr.success()) {
            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Fail:" + mr.msg(), Toast.LENGTH_SHORT).show();
        }
    }
}