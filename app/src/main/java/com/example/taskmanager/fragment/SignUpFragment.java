package com.example.taskmanager.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taskmanager.R;
import com.example.taskmanager.activity.SignUpActivity;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.UserDBRepository;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    public static final String ARG_USERNAME = "argUserName";
    public static final String ARG_PASSWORD = "argPassword";
    private static final String TAG = "bashir_SUF";
    private String mUserName;
    private String mPassword;

    private EditText mEditTextUserName;
    private EditText mEditTextPassword;
    private Button mButtonLogin;
    private Button mButtonSignUp;

    private TextInputLayout mTextInputLayoutUserName;
    private TextInputLayout mTextInputLayoutPassword;

    private UserDBRepository mUserDBRepository;

    private CallBack mCallBack;

    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance(String userName, String Password) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, userName);
        args.putString(ARG_PASSWORD, Password);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserName = getArguments().getString(ARG_USERNAME);
            mPassword = getArguments().getString(ARG_PASSWORD);
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
        mButtonLogin.setVisibility(View.GONE);
        mButtonSignUp.setTextSize(10);
        mButtonSignUp.setWidth(200);


        if (mUserName != null) {
            mEditTextUserName.setText(mUserName);
        }
        if (mPassword != null) {
            mEditTextPassword.setText(mPassword);
        }

        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CallBack) {
            mCallBack = (CallBack) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement onCrimeSelected");
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
        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = String.valueOf(mEditTextUserName.getText());
                String password = String.valueOf(mEditTextPassword.getText());
                if (!userName.equals("") && !password.equals("")) {
                    User user = new User(userName, password,new Date());
                    mUserDBRepository.insert(user);
                    Log.d(TAG,"befor callback signup "+userName+"  "+ password);
                    mCallBack.removeFragment(userName, password,SignUpFragment.this);
                    //Intent intent = new Intent();
                    //intent.putExtra(LoginFragment.KEY_USER_NAME,userName);
                    //intent.putExtra(LoginFragment.KEY_PASSWORD, Integer.parseInt(password));
                    //setResult(RESULT_OK,intent);
                    //finish();
                } else {
                    Toast.makeText(getActivity(), "please fill request fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public interface CallBack {
        public void removeFragment(String userName, String password,Fragment fragment);
    }
}