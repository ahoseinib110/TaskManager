package com.example.taskmanager.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;


import com.example.taskmanager.R;
import com.example.taskmanager.model.State;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskDBRepository;
import com.example.taskmanager.utils.DateUtils;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;

public class TaskDetailFragment extends Fragment {

    public static final String TAG = "bashir_TDF";
    public static final String BUNDLE_TASK = "task";
    public static final String ARG_TASK = "TaskArg";
    public static final String DIALOG_FRAGMENT_TAG = "Dialog";
    public static final int DATE_PICKER_REQUEST_CODE = 0;
    public static final int TIME_PICKER_REQUEST_CODE = 1;
    public static final int IMAGE_PICKER_REQUEST_CODE = 2;

    private Task mTask;
    private TaskDBRepository mRepository;

    private EditText mEditTextDetailTitle;
    private EditText mEditTextDetailDescription;
    private Button mButtonDetailDate;
    private Button mButtonDetailTime;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioButtonTodo;
    private RadioButton mRadioButtonDoing;
    private RadioButton mRadioButtonDone;
    private TextView mTextViewDetailDelete;
    private TextView mTextViewDetailCancle;
    private TextView mTextViewDetailEdit;
    private TextView mTextViewDetailSave;

    private TextInputLayout mTextInputLayoutTitle;
    private TextInputLayout mTextInputLayoutDescription;

    private ImageView mImageViewDetail;

    private Button mButtonSetImage;

    public TaskDetailFragment() {
        //empty public constructor
    }


    public static TaskDetailFragment newInstance(Task task) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK, task);
        TaskDetailFragment fragment = new TaskDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mTask = (Task) getArguments().getSerializable(ARG_TASK);
        mRepository = TaskDBRepository.getInstance(getActivity(), mTask.getUserId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);

        findViews(view);
        initViews();
        setListeners();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_task_detail,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_item_save:
                saveTask();
                return true;
            case R.id.menu_item_delete:
                mRepository.delete(mTask);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(BUNDLE_TASK, mTask);
    }

    //@Override
    //public void onPause() {
    //    super.onPause();
    //    updateTask();
    //}

    private void findViews(View view) {
        mTextInputLayoutTitle = view.findViewById(R.id.textInputLayoutTitle);
        mTextInputLayoutDescription = view.findViewById(R.id.textInputLayoutDescription);
        mEditTextDetailTitle = mTextInputLayoutTitle.getEditText();
        mEditTextDetailDescription = mTextInputLayoutDescription.getEditText();
        mButtonDetailDate = view.findViewById(R.id.buttonDetailDate);
        mButtonDetailTime = view.findViewById(R.id.buttonDetailTime);
        mRadioGroup = view.findViewById(R.id.radioGroup);
        mRadioButtonTodo = view.findViewById(R.id.radioButtonTodo);
        mRadioButtonDoing = view.findViewById(R.id.radioButtonDoing);
        mRadioButtonDone = view.findViewById(R.id.radioButtonDone);
        mTextViewDetailDelete = view.findViewById(R.id.textViewDetailDelete);
        mTextViewDetailCancle = view.findViewById(R.id.textViewDetailCancle);
        mTextViewDetailEdit = view.findViewById(R.id.textViewDetailEdit);
        mTextViewDetailSave = view.findViewById(R.id.textViewDetailSave);
        mImageViewDetail = view.findViewById(R.id.imageViewDetail);
        mButtonSetImage = view.findViewById(R.id.buttonSetImage);
    }

    private void initViews() {
        mEditTextDetailTitle.setText(mTask.getTaskTitle());
        mEditTextDetailDescription.setText(mTask.getTaskDescription());
        mButtonDetailDate.setText(DateUtils.getDateWithoutTime(mTask.getDate()));
        mButtonDetailTime.setText(DateUtils.getTimeWithoutDate(mTask.getDate()));
        //mCheckboxDetailState.setChecked(false);//mTask.isSolved()
        switch (mTask.getTaskState()) {
            case TODO:
                ((RadioButton) mRadioGroup.getChildAt(0)).setChecked(true);
                break;
            case DOING:
                ((RadioButton) mRadioGroup.getChildAt(1)).setChecked(true);
                break;
            case DONE:
                ((RadioButton) mRadioGroup.getChildAt(2)).setChecked(true);
                break;
        }
        if (mTask.getTaskTitle().equals("")) {
            mTextViewDetailCancle.setVisibility(View.VISIBLE);
            mTextViewDetailEdit.setVisibility(View.INVISIBLE);
            mTextViewDetailDelete.setVisibility(View.GONE);
        } else {
            mTextViewDetailCancle.setVisibility(View.GONE);
            mTextViewDetailEdit.setVisibility(View.VISIBLE);
            mTextViewDetailDelete.setVisibility(View.VISIBLE);

            mEditTextDetailTitle.setEnabled(false);
            mEditTextDetailDescription.setEnabled(false);
            mButtonDetailTime.setEnabled(false);
            mButtonDetailDate.setEnabled(false);
            mRadioButtonTodo.setEnabled(false);
            mRadioButtonDoing.setEnabled(false);
            mRadioButtonDone.setEnabled(false);
        }

    }

    private void setListeners() {

        mButtonDetailDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(mTask.getDate());
                //create parent-child relations between CrimeDetailFragment-DatePickerFragment
                datePickerFragment.setTargetFragment(TaskDetailFragment.this, DATE_PICKER_REQUEST_CODE);
                datePickerFragment.show(getFragmentManager(), DIALOG_FRAGMENT_TAG);
            }
        });


        mButtonDetailTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(mTask.getDate());
                timePickerFragment.setTargetFragment(TaskDetailFragment.this, TIME_PICKER_REQUEST_CODE);
                timePickerFragment.show(getFragmentManager(), DIALOG_FRAGMENT_TAG);
            }
        });

        mTextViewDetailCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dismiss();
            }
        });

        mTextViewDetailDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRepository.delete(mTask);
                //dismiss();
            }
        });

        mTextViewDetailEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditTextDetailTitle.setEnabled(true);
                mEditTextDetailDescription.setEnabled(true);
                mButtonDetailTime.setEnabled(true);
                mButtonDetailDate.setEnabled(true);
                mRadioButtonTodo.setEnabled(true);
                mRadioButtonDoing.setEnabled(true);
                mRadioButtonDone.setEnabled(true);
            }
        });

        mTextViewDetailSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask();
            }
        });


        mButtonSetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
    }

    private void saveTask() {
        int radioButtonID = mRadioGroup.getCheckedRadioButtonId();
        View radioButton = mRadioGroup.findViewById(radioButtonID);
        int idx = mRadioGroup.indexOfChild(radioButton);
        Log.d("bashir", "index" + idx);
        switch (idx) {
            case 0:
                mTask.setTaskState(State.TODO);
                break;
            case 1:
                mTask.setTaskState(State.DOING);
                break;
            case 2:
                mTask.setTaskState(State.DONE);
                break;
        }
        mTask.setTaskTitle(String.valueOf(mEditTextDetailTitle.getText()));
        mTask.setTaskDescription(String.valueOf(mEditTextDetailDescription.getText()));

        if (mTextViewDetailEdit.getVisibility() == View.GONE || mTextViewDetailEdit.getVisibility() == View.INVISIBLE) {
            mRepository.insert(mTask);
        } else {
            mRepository.update(mTask);
        }
        //Log.d("bashir","size af add  " +mRepository.getList(mTask.getTaskState()).size());
        //dismiss();
    }

    private void openGallery() {
        Log.d(TAG,"open gallery");
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        Intent intent = Intent.createChooser(gallery, null);
        startActivityForResult(intent, IMAGE_PICKER_REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK || data == null)
            return;
        if (requestCode == DATE_PICKER_REQUEST_CODE) {
            //get response from intent extra, which is user selected date
            Date userSelectedDate = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_USER_SELECTED_DATE);
            mTask.setDate(userSelectedDate);
            mButtonDetailDate.setText(DateUtils.getDateWithoutTime(mTask.getDate()));
        } else if (requestCode == TIME_PICKER_REQUEST_CODE) {
            Date userSelectedDate = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_USER_SELECTED_TIME);
            mTask.setDate(userSelectedDate);
            mButtonDetailTime.setText(DateUtils.getTimeWithoutDate(mTask.getDate()));
        }else if (requestCode == IMAGE_PICKER_REQUEST_CODE){
            Uri imageUri = data.getData();
            mImageViewDetail.setImageURI(imageUri);
        }
    }



    /*void onResultFromDatePicker(Date userPickedDate) {
        mCrime.setDate(userPickedDate);
        mButtonDate.setText(mCrime.getDate().toString());

        updateCrime();
    }*/
}