package com.example.firechat.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firechat.R;
import com.example.firechat.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SingInActivity extends AppCompatActivity {
    private static final String TAG = "SingInActivity";
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText repeatPasswordEditText;
    private EditText nameEditText;
    private androidx.appcompat.widget.AppCompatButton loginSingInButton;
    private TextView toggleLoginSingUpTextView;
    private boolean loginModeActive;
    private ActionBar actionBar;
    private FirebaseAuth auth;

    private FirebaseDatabase database;
    private DatabaseReference usersDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_sing_in);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance(ChatActivity.BASE_URL);
        usersDatabaseReference = database.getReference().child("users");

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        nameEditText = findViewById(R.id.nameEditText);
        loginSingInButton = findViewById(R.id.loginSingInButton);
        toggleLoginSingUpTextView = findViewById(R.id.toggleLoginSingUpTextView);
        repeatPasswordEditText = findViewById(R.id.repeatPasswordEditText);

        loginSingInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginSingInUser(emailEditText.getText().toString().trim(), passwordEditText.getText().toString().trim());
            }
        });
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(SingInActivity.this, UserListActivity.class));
        }
    }

    private void loginSingInUser(String email, String password) {
        if (loginModeActive) {
            if (passwordEditText.getText().toString().trim().length() < 7) {
                Toast.makeText(this, "Пароль должен содержать больше 7 символов", Toast.LENGTH_SHORT).show();
            } else if (emailEditText.getText().toString().trim().equals("")) {
                Toast.makeText(this, "Введите e-mail", Toast.LENGTH_SHORT).show();
                //} else if (nameEditText.getText().toString().trim().equals("")) {
                //    Toast.makeText(this, "Введите имя", Toast.LENGTH_SHORT).show();
            } else {
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = auth.getCurrentUser();
                                    Intent intent = new Intent(SingInActivity.this, UserListActivity.class);
                                    intent.putExtra("userName", nameEditText.getText().toString().trim());
                                    startActivity(intent);
                                    //updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(SingInActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }
                            }
                        });
            }
        } else {
            if (!passwordEditText.getText().toString().trim().equals(repeatPasswordEditText.getText().toString().trim())) {
                Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
            } else if (passwordEditText.getText().toString().trim().length() < 7) {
                Toast.makeText(this, "Пароль должен содержать больше 7 символов", Toast.LENGTH_SHORT).show();
            } else if (emailEditText.getText().toString().trim().equals("")) {
                Toast.makeText(this, "Введите e-mail", Toast.LENGTH_SHORT).show();
            } else if (nameEditText.getText().toString().trim().equals("")) {
                Toast.makeText(this, "Введите имя", Toast.LENGTH_SHORT).show();
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = auth.getCurrentUser();
                                    createUser(user);
                                    Intent intent = new Intent(SingInActivity.this, UserListActivity.class);
                                    intent.putExtra("userName", nameEditText.getText().toString().trim());
                                    startActivity(intent);
                                    //updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(SingInActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }
                            }
                        });
            }
        }
    }

    private void createUser(FirebaseUser firebaseUser) {
        User user = new User();
        user.setId(firebaseUser.getUid());
        user.setEmail(firebaseUser.getEmail());
        user.setName(nameEditText.getText().toString().trim());
        usersDatabaseReference.push().setValue(user);
    }

    public void toggleLoginMode(View view) {
        if (loginModeActive) {
            loginModeActive = false;
            loginSingInButton.setText("Регистрация");
            toggleLoginSingUpTextView.setText("Уже есть аккаунт? Войти");
            repeatPasswordEditText.setVisibility(View.VISIBLE);
            nameEditText.setVisibility(View.VISIBLE);
        } else {
            loginModeActive = true;
            loginSingInButton.setText("Войти");
            toggleLoginSingUpTextView.setText("Зарегистрироваться");
            repeatPasswordEditText.setVisibility(View.GONE);
            nameEditText.setVisibility(View.GONE);
        }

    }
}