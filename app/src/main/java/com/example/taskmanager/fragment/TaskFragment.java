package com.example.taskmanager.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.taskmanager.R;
import com.example.taskmanager.activity.TaskManagerActivity;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFragment extends Fragment {

    private EditText mEditTextUserName;
    private EditText mEditTextTaskNumber;
    private Button mButtonCreateTasks;

    public TaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskFragment newInstance() {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        findViews(view);
        setOnclickListener();
        return view;
    }

    private void setOnclickListener() {
        mButtonCreateTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = TaskManagerActivity.newIntent(getActivity()
               //         , String.valueOf(mEditTextUserName.getText())
               //         , Integer.parseInt(String.valueOf(mEditTextTaskNumber.getText())));
               // getActivity().startActivity(intent);
             }
        });
    }

    private void findViews(View view) {
        mEditTextUserName = view.findViewById(R.id.editTextUserName);
        mEditTextTaskNumber = view.findViewById(R.id.editTextTaskNumber);
        mButtonCreateTasks = view.findViewById(R.id.buttonCreateTasks);
    }


}