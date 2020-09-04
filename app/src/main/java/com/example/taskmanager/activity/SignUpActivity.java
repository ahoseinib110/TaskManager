package com.example.taskmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taskmanager.R;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.UserDBRepository;
import com.google.android.material.textfield.TextInputLayout;


public class SignUpActivity extends AppCompatActivity {
    private EditText mEditTextUserName;
    private EditText mEditTextPassword;
    private Button mButtonLogin;
    private Button mButtonSignUp;

    private TextInputLayout mTextInputLayoutUserName;
    private TextInputLayout mTextInputLayoutPassword;

    private UserDBRepository mUserDBRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUserDBRepository = UserDBRepository.getInstance(this);
        findViews();
        setOnClickListeners();
        mButtonLogin.setVisibility(View.GONE);
        mButtonSignUp.setTextSize(10);
        mButtonSignUp.setWidth(200);
        Intent intent = getIntent();
        String username = intent.getStringExtra(LoginActivity.KEY_USER_NAME);
        int password = intent.getIntExtra(LoginActivity.KEY_PASSWORD,0);

        if(username !=null){
            mEditTextUserName.setText(String.valueOf(username));
        }
        if(password!=0){
            mEditTextPassword.setText(String.valueOf(password));
        }
    }

    private void findViews() {
        mTextInputLayoutUserName = findViewById(R.id.textInputLayoutUserName);
        mTextInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        mEditTextUserName = mTextInputLayoutUserName.getEditText();
        mEditTextPassword = mTextInputLayoutPassword.getEditText();
        mButtonLogin = findViewById(R.id.buttonLogin);
        mButtonSignUp = findViewById(R.id.buttonSignUp);
    }

    private void setOnClickListeners() {
        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = String.valueOf(mEditTextUserName.getText());
                String password = String.valueOf(mEditTextPassword.getText());
                if(!userName.equals("") && !password.equals("")){
                    User user = new User(userName,password);
                    mUserDBRepository.insert(user);
                    Intent intent = new Intent();
                    intent.putExtra(LoginActivity.KEY_USER_NAME,userName);
                    intent.putExtra(LoginActivity.KEY_PASSWORD, Integer.parseInt(password));
                    setResult(RESULT_OK,intent);
                    finish();
                }else{
                    Toast.makeText(SignUpActivity.this, "please fill request fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}