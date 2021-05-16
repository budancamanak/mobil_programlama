package tr.yildiz.edu.l1108080.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import tr.yildiz.edu.l1108080.R;
import tr.yildiz.edu.l1108080.repository.IRepository;
import tr.yildiz.edu.l1108080.repository.RepositoryService;
import tr.yildiz.edu.l1108080.repository.models.User;
import tr.yildiz.edu.l1108080.util.Constants;
import tr.yildiz.edu.l1108080.util.MethodResponse;

public class RegisterActivity extends AppCompatActivity {

    EditText txtEmail, txtName, txtSurname, txtPassword, txtPasswordAgain, txtPhone;
    Button btnCancel, btnRegister;
    IRepository<User> repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        repo = RepositoryService.get().getRepository(this, Constants.RepositoryNameSpace.User);
        txtEmail = findViewById(R.id.txtEmail);
        txtName = findViewById(R.id.txtName);
        txtSurname = findViewById(R.id.txtSurname);
        txtPassword = findViewById(R.id.txtPassword);
        txtPasswordAgain = findViewById(R.id.txtPasswordAgain);
        txtPhone = findViewById(R.id.txtPhone);

        btnCancel = findViewById(R.id.btnCancel);
        btnRegister = findViewById(R.id.btnRegister);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateInputs())
                    return;
                User item = new User();
                item.email(txtEmail.getText().toString()).name(txtName.getText().toString()).surname(txtSurname.getText().toString());
                item.password(txtPassword.getText().toString()).phone(txtPhone.getText().toString());
                MethodResponse mr = repo.create(item);
                if (mr.success()) {
                    Toast.makeText(getApplicationContext(), "User registered", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Registration failed:" + mr.msg(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    boolean validateInputs() {
        return true;
    }
}