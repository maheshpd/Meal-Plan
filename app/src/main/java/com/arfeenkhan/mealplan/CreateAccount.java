package com.arfeenkhan.mealplan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccount extends AppCompatActivity {

    //Widget
    EditText medt_name, medt_email, medt_password;
    Button mfacebook_btn, mgoogle_btn, mcreate_acc;
    ProgressDialog mprogressDialog;
    //Firebase
    FirebaseAuth mAuth;
    DatabaseReference mUserRef;


    //String
    String user_name, user_pass, user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //initialize waidget
        initWidget();

        //Init firebase
        mAuth = FirebaseAuth.getInstance();
        mUserRef = FirebaseDatabase.getInstance().getReference("Users");

        mcreate_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_email = medt_email.getText().toString().trim();
                user_name = medt_name.getText().toString().trim();
                user_pass = medt_password.getText().toString().trim();
                if (TextUtils.isEmpty(user_name)) {
                    medt_name.setHint("User Name Needed");
                } else if (TextUtils.isEmpty(user_email)) {
                    medt_email.setHint("Email Id Needed");
                } else if (TextUtils.isEmpty(user_pass)) {
                    medt_password.setHint("Password Needed");
                } else {
                    mprogressDialog.setTitle("Please wait..");
                    mprogressDialog.setCanceledOnTouchOutside(false);
                    mprogressDialog.show();
                    mAuth.createUserWithEmailAndPassword(user_email, user_pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        mAuth.getCurrentUser().sendEmailVerification()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            mprogressDialog.dismiss();
                                                            Toast.makeText(CreateAccount.this, "Registered successfully. Please verify Email address", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            mprogressDialog.dismiss();
                                                            Toast.makeText(CreateAccount.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    } else {
                                        mprogressDialog.dismiss();
                                        Toast.makeText(CreateAccount.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void initWidget() {
        medt_email = findViewById(R.id.edt_email);
        medt_name = findViewById(R.id.edt_name);
        medt_password = findViewById(R.id.edt_password);
        mfacebook_btn = findViewById(R.id.facebook_btn);
        mgoogle_btn = findViewById(R.id.google_btn);
        mcreate_acc = findViewById(R.id.create_account);

        mprogressDialog = new ProgressDialog(this);
    }


    public void LoginPage(View view) {
        startActivity(new Intent(CreateAccount.this, Login.class));
    }
}
