package com.example.taskmanager.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import com.example.taskmanager.R;
import com.example.taskmanager.utils.DateUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SetImageFragment extends DialogFragment {

    private static final String TAG = "bashir_SIF";

    public static final String EXTRA_IMAGE_URI ="imageUri";
    public static final int REQUEST_CODE_FROM_GALLERY = 10;
    public static final int REQUEST_CODE_FROM_CAMERA = 11;

    private Uri mImageUri;

    public static SetImageFragment newInstance() {
        return new SetImageFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final CharSequence[] items = {"Camera", "Gallery"};
        // Use the Builder class for convenient dialog construction
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
        return builder.create();
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
        Intent intent = Intent.createChooser(gallery, null);
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
        //currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void setResult() {
        TaskDetailFragment taskDetailFragment = (TaskDetailFragment) getTargetFragment();
        Intent intent = new Intent();
        intent.putExtra(EXTRA_IMAGE_URI,mImageUri);
        Log.d(TAG,mImageUri.toString());
        taskDetailFragment.onActivityResult(TaskDetailFragment.REQUEST_CODE_SET_IMAGE, Activity.RESULT_OK, intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK )
            return;

        if (requestCode == REQUEST_CODE_FROM_CAMERA){
            setResult();
            dismiss();
        }

        if(data == null){
            return;
        }

        if (requestCode == REQUEST_CODE_FROM_GALLERY){
            mImageUri = data.getData();
            setResult();
            dismiss();
        }
    }
}