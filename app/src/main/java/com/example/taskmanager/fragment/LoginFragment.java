package com.example.taskmanager.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taskmanager.R;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.UserDBRepository;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    public static final String KEY_USER_NAME = "userName";
    public static final String KEY_PASSWORD = "password";
    public static final int REQUEST_CODE_SIGN_UP = 0;
    public static final String TAG = "bashir_LA";
    public static final String BOUNDLE_USERNAME = "BoundleString";
    private static final String BOUNDLE_PASSWORD = "BoundlePassword";

    private EditText mEditTextUserName;
    private EditText mEditTextPassword;
    private Button mButtonLogin;
    private Button mButtonSignUp;
    private TextInputLayout mTextInputLayoutUserName;
    private TextInputLayout mTextInputLayoutPassword;

    private UserDBRepository mUserDBRepository;
    private String mUserName;
    private String mPassword;

    private CallBack mCallBack;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String userName, String password) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(BOUNDLE_USERNAME, userName);
        args.putString(BOUNDLE_PASSWORD, password);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserName = getArguments().getString(BOUNDLE_USERNAME);
            mPassword = getArguments().getString(BOUNDLE_PASSWORD);
        }
        mUserDBRepository = UserDBRepository.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        findViews(view);
        setOnClickListeners();
        if(mUserName!=null) {
            mEditTextUserName.setText(mUserName);
        }
        if(mPassword!=null) {
            mEditTextPassword.setText(mPassword);
        }
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof CallBack){
            mCallBack = (CallBack) context;
        }else {

        }
    }

    private void findViews(View view) {
        mTextInputLayoutUserName = view.findViewById(R.id.textInputLayoutUserName);
        mTextInputLayoutPassword = view.findViewById(R.id.textInputLayoutPassword);
        mEditTextUserName = mTextInputLayoutUserName.getEditText();
        mEditTextPassword = mTextInputLayoutPassword.getEditText();
        mButtonLogin = view.findViewById(R.id.buttonLogin);
        mButtonSignUp = view.findViewById(R.id.buttonSignUp);
    }

    private void setOnClickListeners() {
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmptyUserName() || isEmptyPassword()) {
                    Toast.makeText(getActivity(), "please fill request fields", Toast.LENGTH_SHORT).show();
                } else {
                    String userName = String.valueOf(mEditTextUserName.getText());
                    String password = String.valueOf(mEditTextPassword.getText());
                    User user = mUserDBRepository.get(userName);
                    if (user != null) {
                        if (Integer.parseInt(user.getPassword()) == Integer.parseInt(password)) {
                            Toast.makeText(getActivity(), "correct", Toast.LENGTH_SHORT).show();
                            mCallBack.startTaskManagerActivity(user.getUserId());
                        } else {
                            Toast.makeText(getActivity(), "user name or password is incorrect!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "please sign up first", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = null,password = null;
                if (!isEmptyUserName()) {
                    userName= String.valueOf(mEditTextUserName.getText());
                }
                if (!isEmptyPassword()) {
                    password = String.valueOf(mEditTextPassword.getText());
                }
                mCallBack.startSignUpActivity(userName,password);
            }
        });
    }

    public boolean isEmptyUserName() {
        if (String.valueOf(mEditTextUserName.getText()).equals("")) {
            return true;
        }
        return false;
    }

    public boolean isEmptyPassword() {
        if (String.valueOf(mEditTextPassword.getText()).equals("")) {
            return true;
        }
        return false;
    }

    public interface CallBack{
        public void startSignUpActivity(String userName, String password);
        public void startTaskManagerActivity(int userId);
    }


}