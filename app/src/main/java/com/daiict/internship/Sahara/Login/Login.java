package com.daiict.internship.Sahara.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;

import com.daiict.internship.Sahara.LoginSignUPDashboard.LoginSignUpDashboard;
import com.daiict.internship.Sahara.R;
import com.daiict.internship.Sahara.SignUp.SelectionCategory;
import com.daiict.internship.Sahara.UserDashboard.BottomNavigationUsers;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Login extends AppCompatActivity {

    TextInputEditText edt_email, edt_pass;
    Button btnlogin;

    private FirebaseAuth mAuth;
    DatabaseReference ref;

    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edt_email = findViewById(R.id.login_edit_username);
        edt_pass = findViewById(R.id.login_edit_password);

        btnlogin = findViewById(R.id.login_btn_login);

        mAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
    }

    public void loginBack(View view) {
        Intent intent = new Intent(Login.this, LoginSignUpDashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void loginForgotPassword(View view) {
        Intent intent = new Intent(Login.this, ForgotPassword.class);
        startActivity(intent);

    }

    public void btnLoginOnClick(View view) {
        if (validateEmailAddress() && validatePassword()) {
            btnlogin.setEnabled(false);
            // Progress Dialog Here......
            Log.e("btnLoginOnClick: ", "Email and Password");
            String emailID = edt_email.getText().toString().trim();
            String password = edt_pass.getText().toString();

            mAuth.signInWithEmailAndPassword(emailID, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Authentication Successfull...
                        Log.e("btnLoginOnClick: ", "Authentication Successfull");
                        userId = mAuth.getUid();

                        if (userId != null) {
                            if (Objects.requireNonNull(mAuth.getCurrentUser()).isEmailVerified()) {
                                Log.e("btnLoginOnClick: ", "Login Successfull");
                                // Intent to Loading Activity
                                Intent intent = new Intent(Login.this, CovidInfoActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                View rootView = getWindow().getDecorView().getRootView();
                                Log.e("btnLoginOnClick: ", "Email is Not Verified");
                                Snackbar verifyEmailMsg = Snackbar.make(rootView, "Please Verify your Email Address First!", Snackbar.LENGTH_LONG);
                                verifyEmailMsg.show();
                                mAuth.signOut();
                                btnlogin.setEnabled(true);
                            }
                        }

                    } else {
                        Log.e("btnLoginOnClick: ", "Authentication Failed!!!");
                        View rootView = getWindow().getDecorView().getRootView();
                        Snackbar authenticationFailed = Snackbar.make(rootView, "Authentication Failed", Snackbar.LENGTH_SHORT);
                        authenticationFailed.show();
                        btnlogin.setEnabled(true);
                    }
                }
            });
        }
    }

    private String fetchUserCategory(final String userId) {

        final String[] result = new String[1];

        ref.child("Needy").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Log.e("User Role", "onDataChange: " + userId + " is available in Needy");
                    result[0] = "Needy";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ref.child("NGO").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Log.e("User Role", "onDataChange: " + userId + " is available in NGO");
                    result[0] = "NGO";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ref.child("Volunteer").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Log.e("User Role", "onDataChange: " + userId + " is available in Volunteer");
                    result[0] = "Volunteer";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ref.child("Donor").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Log.e("User Role", "onDataChange: " + userId + " is available in Donor");
                    result[0] = "Donor";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return result[0];
    }

    public void loginCreateAccount(View view) {
        Intent intent = new Intent(Login.this, SelectionCategory.class);
        startActivity(intent);
        finish();
    }

    private boolean validateEmailAddress() {
        String email = edt_email.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            edt_email.setError("Please Enter Email Address");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edt_email.setError("Please Enter Valid Email");
            return false;
        } else {
            edt_email.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String password = edt_pass.getText().toString();

        if (TextUtils.isEmpty(password)) {
            View rootView = getWindow().getDecorView().getRootView();
            Snackbar notEmptyErrorMsg = Snackbar.make(rootView, "Password Cannot Be Empty.", Snackbar.LENGTH_SHORT);
            notEmptyErrorMsg.show();
            return false;
        } else {
            return true;
        }
    }
}

/*



         */
