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

import java.util.HashMap;

import in.thelosergeek.community_app.MainActivity;
import in.thelosergeek.community_app.R;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText name, email, password, repassword;
    private TextView btnlogin;
    private Button btnreg;
    private FirebaseAuth mAuth;
    private ProgressDialog Regprogess;
    private DatabaseReference firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (TextInputEditText) findViewById(R.id.name);
        email = (TextInputEditText) findViewById(R.id.email);
        password = (TextInputEditText) findViewById(R.id.password);
        repassword = (TextInputEditText) findViewById(R.id.repassword);
        btnlogin = findViewById(R.id.btnlogin);
        btnreg = findViewById(R.id.btnreg);
        mAuth = FirebaseAuth.getInstance();
        Regprogess = new ProgressDialog(this);


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reg_display_name = name.getEditableText().toString();
                String reg_email = email.getText().toString();
                String reg_password = password.getText().toString();
                String reg_re_password = repassword.getText().toString();

                if (!TextUtils.isEmpty(reg_display_name) || !TextUtils.isEmpty(reg_email) || !TextUtils.isEmpty(reg_password)  || !TextUtils.isEmpty(reg_re_password)) {
                    Regprogess.setTitle("Registering");
                    Regprogess.setMessage("Please Wait");
                    Regprogess.setCanceledOnTouchOutside(false);
                    Regprogess.show();;

                    registeruser(reg_display_name, reg_email, reg_password);
                }

            }
        });



    }

    private void registeruser(final String reg_display_name, final String reg_email, String reg_password) {
        mAuth.createUserWithEmailAndPassword(reg_email, reg_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();

                    String uid = current_user.getUid();
                    firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("name", reg_display_name);
                    userMap.put("image", "default");
                    userMap.put("thumb_image", "default");
                    userMap.put("email",reg_email);

                    firebaseDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                Regprogess.dismiss();
                                Toast.makeText(RegisterActivity.this,"Successfully Registered",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });

                }
                else{
                    Regprogess.hide();
                    Toast.makeText(RegisterActivity.this,"Not Registered",Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}