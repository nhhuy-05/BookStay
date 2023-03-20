package vn.edu.fpt.projectprm392.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import vn.edu.fpt.projectprm392.R;
import vn.edu.fpt.projectprm392.models.Hotel;
import vn.edu.fpt.projectprm392.models.User;

public class SignUpActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword, edtRePassword;
    private Button btnRegister;
    private TextView tvSwitchLogin;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private ProgressBar pgbRegister;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Get View
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        edtRePassword = findViewById(R.id.edt_re_password);
        btnRegister = findViewById(R.id.btn_register);
        tvSwitchLogin = findViewById(R.id.tv_switch_login);
        pgbRegister = findViewById(R.id.pgb_register);
        // Get Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Get Firebase Database
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("Users");

        // Set visibility for button and progress bar
        pgbRegister.setVisibility(View.VISIBLE);

        // Switch to Login
        tvSwitchLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Register
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRegister.setVisibility(View.INVISIBLE);
                pgbRegister.setVisibility(View.VISIBLE);
                String emailText = String.valueOf(edtUsername.getText().toString());
                String passwordText = String.valueOf(edtPassword.getText().toString());
                String rePasswordText = String.valueOf(edtRePassword.getText().toString());
                if (emailText.isEmpty()) {
                    edtUsername.setError("Please enter your email");
                    pgbRegister.setVisibility(View.GONE);
                    btnRegister.setVisibility(View.VISIBLE);
                    return;
                }
                if (passwordText.isEmpty()) {
                    edtPassword.setError("Please enter your password");
                    pgbRegister.setVisibility(View.GONE);
                    btnRegister.setVisibility(View.VISIBLE);
                    return;
                }
                if (rePasswordText.isEmpty()) {
                    edtRePassword.setError("Please enter your password");
                    pgbRegister.setVisibility(View.GONE);
                    btnRegister.setVisibility(View.VISIBLE);
                    return;
                }
                if (!passwordText.equals(rePasswordText)) {
                    edtRePassword.setError("Password does not match");
                    pgbRegister.setVisibility(View.GONE);
                    btnRegister.setVisibility(View.VISIBLE);
                    return;
                }

                mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                pgbRegister.setVisibility(View.GONE);
                                btnRegister.setVisibility(View.VISIBLE);
                                if (task.isSuccessful()) {
                                    // Get the current user's ID
                                    String userId = mAuth.getCurrentUser().getUid();
                                    // Create a new user with a first and last name
                                    User user = new User(userId, "", emailText, "", "user");
                                    // Add a new document with a generated ID
                                    userRef.child(userId).setValue(user);
                                    Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    // Get User by Email
    public void getUserByEmail(String email) {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user.getEmail().equals(email)) {
                        edtUsername.setError("Email is already registered");
                        pgbRegister.setVisibility(View.GONE);
                        btnRegister.setVisibility(View.VISIBLE);
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
