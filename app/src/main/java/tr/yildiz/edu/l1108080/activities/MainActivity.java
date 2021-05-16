package tr.yildiz.edu.l1108080.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import tr.yildiz.edu.l1108080.R;
import tr.yildiz.edu.l1108080.repository.IRepository;
import tr.yildiz.edu.l1108080.repository.RepositoryService;
import tr.yildiz.edu.l1108080.repository.models.User;
import tr.yildiz.edu.l1108080.repository.repos.UserRepository;
import tr.yildiz.edu.l1108080.util.Constants;

public class MainActivity extends Activity {

    EditText txtEmail, txtPassword;
    Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IRepository<User> repo = RepositoryService.get().getRepository(this, Constants.RepositoryNameSpace.User);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateInputs())
                    return;
                User item = ((UserRepository) repo).getByMail(txtEmail.getText().toString());
                if (item == null || !item.password().equalsIgnoreCase(txtPassword.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intent);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    boolean validateInputs() {
        String email = txtEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please provide an email", Toast.LENGTH_SHORT).show();
            return false;
        }
        String pass = txtPassword.getText().toString();
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(getApplicationContext(), "Please provide a password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}