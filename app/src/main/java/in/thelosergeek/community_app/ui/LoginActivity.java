package in.thelosergeek.community_app.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import in.thelosergeek.community_app.MainActivity;
import in.thelosergeek.community_app.R;


public class LoginActivity extends AppCompatActivity {

    private TextInputEditText log_email, log_password;
    private Button login;
    private TextView register;
    private FirebaseAuth mAuth;
    private DatabaseReference muserdatabase;
    private ProgressDialog loginProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginProgress = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        muserdatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        log_email = (TextInputEditText) findViewById(R.id.email);
        log_password = (TextInputEditText) findViewById(R.id.password);
        login = findViewById(R.id.btnlogin);
        register = findViewById(R.id.btnreg);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = log_email.getText().toString();
                String password = log_password.getText().toString();

                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){
                    loginProgress.setTitle("Logging in");
                    loginProgress.setMessage("Please Wait");
                    loginProgress.setCanceledOnTouchOutside(false);
                    loginProgress.show();
                    loginuser(email,password);
                }
            }
        });


    }

    private void loginuser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    loginProgress.dismiss();
                    Toast.makeText(LoginActivity.this,"Successfully Logged in",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    FirebaseUser user = mAuth.getCurrentUser();

                    startActivity(intent);
                    finish();
                }
                else{
                    loginProgress.hide();
                    Toast.makeText(LoginActivity.this,"Cannot Sign In", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}