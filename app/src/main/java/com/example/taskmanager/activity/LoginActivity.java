package com.example.taskmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taskmanager.R;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.UserDBRepository;


public class LoginActivity extends AppCompatActivity {

    public static final String KEY_USER_NAME = "userName";
    public static final String KEY_PASSWORD = "password";
    public static final int REQUEST_CODE_SIGN_UP = 0;
    public static final String TAG = "bashir_LA";
    private EditText mEditTextUserName;
    private EditText mEditTextPassword;
    private Button mButtonLogin;
    private Button mButtonSignUp;

    private UserDBRepository mUserDBRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUserDBRepository = UserDBRepository.getInstance(this);
        findViews();
        setOnClickListeners();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode!=RESULT_OK || data==null){
            return;
        }
        if(requestCode==REQUEST_CODE_SIGN_UP){
            mEditTextUserName.setText(data.getStringExtra(KEY_USER_NAME));
            mEditTextPassword.setText(String.valueOf(data.getIntExtra(KEY_PASSWORD,0)));
        }
    }

    private void findViews() {
        mEditTextUserName = findViewById(R.id.editTextUserName);
        mEditTextPassword = findViewById(R.id.editTextPassword);
        mButtonLogin = findViewById(R.id.buttonLogin);
        mButtonSignUp = findViewById(R.id.buttonSignUp);
    }

    private void setOnClickListeners() {
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmptyUserName() || isEmptyPassword()) {
                    Toast.makeText(LoginActivity.this, "please fill request fields", Toast.LENGTH_SHORT).show();
                }else{
                    String userName = String.valueOf(mEditTextUserName.getText());
                    String password = String.valueOf(mEditTextPassword.getText());
                    User user = mUserDBRepository.get(userName);
                    if (user != null) {
                        if (Integer.parseInt(user.getPassword()) == Integer.parseInt(password)) {
                            Toast.makeText(LoginActivity.this, "correct", Toast.LENGTH_SHORT).show();
                            Intent intent = TaskManagerActivity.newIntent(LoginActivity.this,user.getUserId());
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "user name or password is incorrect!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "please sign up first", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                if(!isEmptyUserName()) {
                    intent.putExtra(KEY_USER_NAME, String.valueOf(mEditTextUserName.getText()));
                }
                if(!isEmptyPassword()){
                    intent.putExtra(KEY_PASSWORD, Integer.parseInt(String.valueOf(mEditTextPassword.getText())));
                }
                startActivityForResult(intent, REQUEST_CODE_SIGN_UP);
            }
        });
    }

    public boolean isEmptyUserName(){
        if(String.valueOf(mEditTextUserName.getText()).equals("") ){
            return true;
        }
        return false;
    }

    public boolean isEmptyPassword(){
        if( String.valueOf(mEditTextPassword.getText()).equals("")){
            return true;
        }
        return false;
    }
}