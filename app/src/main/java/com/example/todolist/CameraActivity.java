package com.example.todolist;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import java.io.File;
import java.io.IOException;

import androidx.core.content.FileProvider;

public class CameraActivity extends BaseActivity implements ICamera, IPhotoView{
    private static final int REQUEST_IMAGE_CAPTURE = 3;

    private String photoPath;
    private Uri photoURI;
    private IPhotoReceiver photoReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public void TakePhoto(IPhotoReceiver photoReceiver){
        this.photoReceiver = photoReceiver;

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            DispatchTakePictureIntent();
        }
    }

    private void DispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;

            try {
                photoFile = File.createTempFile("image_", ".jpg", getExternalFilesDir(Environment.DIRECTORY_PICTURES));
                photoPath = photoFile.getPath();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + FILE_PROVIDER, photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

            }else photoReceiver.ReceivePhoto(null);
        }else photoReceiver.ReceivePhoto(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if(resultCode == RESULT_OK){
                AddPicToGallery();
                photoReceiver.ReceivePhoto(photoURI.getPath());
            }else{
                photoReceiver.ReceivePhoto(null);
            }
        }
    }

    private void AddPicToGallery(){
        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, photoPath);

        getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    @Override
    public void OpenGallery(String photoPath){

        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        Uri uri = Uri.parse(photoPath);
        intent.setDataAndType(uri, "image/jpeg");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }
}
