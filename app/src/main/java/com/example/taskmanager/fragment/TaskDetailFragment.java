package com.example.taskmanager.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;


import com.example.taskmanager.R;
import com.example.taskmanager.model.State;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskDBRepository;
import com.example.taskmanager.utils.DateUtils;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskDetailFragment extends Fragment {

    public static final String TAG = "bashir_TDF";
    public static final String BUNDLE_TASK = "task";
    public static final String ARG_TASK = "TaskArg";
    public static final String DIALOG_FRAGMENT_TAG = "Dialog";
    public static final String TAG_DIALOG_FRAGMENT_SET_IMAGE = "Dialog";
    public static final int REQUEST_CODE_DATE_PICKER = 0;
    public static final int REQUEST_CODE_TIME_PICKER = 1;


    public static final int REQUEST_CODE_FROM_GALLERY = 10;
    public static final int REQUEST_CODE_FROM_CAMERA = 11;
    private Uri mImageUri;


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
        if(mTask.getUri()!=null){
            mImageViewDetail.setImageURI(Uri.parse(mTask.getUri()));
        }
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
                datePickerFragment.setTargetFragment(TaskDetailFragment.this, REQUEST_CODE_DATE_PICKER);
                datePickerFragment.show(getFragmentManager(), DIALOG_FRAGMENT_TAG);
            }
        });


        mButtonDetailTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(mTask.getDate());
                timePickerFragment.setTargetFragment(TaskDetailFragment.this, REQUEST_CODE_TIME_PICKER);
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
                //SetImageFragment setImageFragment = SetImageFragment.newInstance();
                //setImageFragment.setTargetFragment(TaskDetailFragment.this, REQUEST_CODE_SET_IMAGE);
                //setImageFragment.show(getFragmentManager(), TAG_DIALOG_FRAGMENT_SET_IMAGE);
                final CharSequence[] items = {"Camera", "Gallery"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select an item to set image")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d(TAG, items[which] + " clicked");
                                switch (which) {
                                    case 0:
                                        getImageFromCamera();
                                        break;
                                    case 1:
                                        getImageFromGallery();
                                        break;
                                }
                            }
                        });
                builder.create().show();

            }
        });
    }
    private void getImageFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                mImageUri = FileProvider.getUriForFile(getActivity(),
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                startActivityForResult(takePictureIntent, REQUEST_CODE_FROM_CAMERA);
            }
        }
    }

    private void getImageFromGallery() {
        Log.d(TAG,"open gallery");
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        Log.d(TAG,"open gallery2");
        Intent intent = Intent.createChooser(gallery, null);
        Log.d(TAG,"open gallery3");
        startActivityForResult(intent, REQUEST_CODE_FROM_GALLERY);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        String currentPhotoPath = image.getAbsolutePath();
        Log.d(TAG,currentPhotoPath);
        return image;
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

        if(mImageUri==null){
            Bitmap bm = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_person);
        }else {
            mTask.setUri(mImageUri.toString());
        }

        if (mTextViewDetailEdit.getVisibility() == View.GONE || mTextViewDetailEdit.getVisibility() == View.INVISIBLE) {
            mRepository.insert(mTask);
        } else {
            mRepository.update(mTask);
        }
        //Log.d("bashir","size af add  " +mRepository.getList(mTask.getTaskState()).size());
        //dismiss();
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK )
            return;

        if (requestCode == REQUEST_CODE_FROM_CAMERA){
            mImageViewDetail.setImageURI(mImageUri);
        }

        if(data == null){
            return;
        }

        if (requestCode == REQUEST_CODE_FROM_GALLERY){
            mImageUri = data.getData();
            mImageViewDetail.setImageURI(mImageUri);

        }else   if (requestCode == REQUEST_CODE_DATE_PICKER) {
            //get response from intent extra, which is user selected date
            Date userSelectedDate = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_USER_SELECTED_DATE);
            mTask.setDate(userSelectedDate);
            mButtonDetailDate.setText(DateUtils.getDateWithoutTime(mTask.getDate()));
        } else if (requestCode == REQUEST_CODE_TIME_PICKER) {
            Date userSelectedDate = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_USER_SELECTED_TIME);
            mTask.setDate(userSelectedDate);
            mButtonDetailTime.setText(DateUtils.getTimeWithoutDate(mTask.getDate()));
        }
    }



    /*void onResultFromDatePicker(Date userPickedDate) {
        mCrime.setDate(userPickedDate);
        mButtonDate.setText(mCrime.getDate().toString());

        updateCrime();
    }*/
}