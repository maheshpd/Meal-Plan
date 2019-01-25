package com.arfeenkhan.mealplan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arfeenkhan.mealplan.Utils.Common;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Login extends AppCompatActivity {

    //Widget
    EditText medt_email, medt_password;
    Button mfacebook_btn, mgoogle_btn, mcreate_acc;
    ProgressDialog mprogressDialog;
    //Firebase
    FirebaseAuth mAuth;
    DatabaseReference mUserRef;

    private static final int RC_SIGN_IN = 1;


    //String
    String user_pass, user_email, current_userid;

    //Google Signin
    GoogleSignInClient mGoogleSignInclient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //init widget
        initWidget();

        //Init firebase
        mAuth = FirebaseAuth.getInstance();
        mUserRef = FirebaseDatabase.getInstance().getReference("Users");

        if (current_userid != null){
            current_userid = mAuth.getCurrentUser().getUid();
        }



        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();

        mGoogleSignInclient = GoogleSignIn.getClient(this, signInOptions);


        mcreate_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_email = medt_email.getText().toString().trim();
                user_pass = medt_password.getText().toString().trim();

                if (TextUtils.isEmpty(user_email)) {
                    medt_email.setHint("Email Id Needed");
                } else if (TextUtils.isEmpty(user_pass)) {
                    medt_password.setHint("Password Needed");
                } else {
                    mprogressDialog.setTitle("Please wait..");
                    mprogressDialog.setCanceledOnTouchOutside(false);
                    mprogressDialog.show();
                    mAuth.signInWithEmailAndPassword(user_email, user_pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        if (mAuth.getCurrentUser().isEmailVerified()) {
                                            mprogressDialog.dismiss();
                                            gotoMain();
                                        } else {
                                            mprogressDialog.dismiss();
                                            Toast.makeText(Login.this, "Please verify your email address", Toast.LENGTH_SHORT).show();
                                        }

                                    } else {
                                        mprogressDialog.dismiss();
                                        Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });

        mgoogle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }

    private void signIn() {
        Intent signIntent = mGoogleSignInclient.getSignInIntent();
        startActivityForResult(signIntent, RC_SIGN_IN);
    }

    private void initWidget() {

        medt_email = findViewById(R.id.edt_email);
        medt_password = findViewById(R.id.edt_password);
        mfacebook_btn = findViewById(R.id.facebook_btn);
        mgoogle_btn = findViewById(R.id.google_btn);
        mcreate_acc = findViewById(R.id.create_account);

        mprogressDialog = new ProgressDialog(this);

    }

    public void SignupPage(View view) {
        startActivity(new Intent(Login.this, CreateAccount.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (account != null) {
            Common.user_name = account.getDisplayName();
            Common.user_email = account.getEmail();
            Common.user_image = account.getPhotoUrl().toString();

            current_userid = mAuth.getCurrentUser().getUid();

            DatabaseReference currentUser = mUserRef.child(current_userid);

            final HashMap<String, String> data = new HashMap<>();
            data.put("name", Common.user_name);
            data.put("email", Common.user_email);
            data.put("profile_image", Common.user_image);
            data.put("phone", "default");
            data.put("token", "token");

            currentUser.setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    gotoMain();
                }
            });
        }
    }

    private void gotoMain() {
        startActivity(new Intent(Login.this, MainActivity.class));
    }

}
