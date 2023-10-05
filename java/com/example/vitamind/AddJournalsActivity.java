package com.example.vitamind;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddJournalsActivity extends AppCompatActivity {

    ImageView backBtn, addPhoto;
    EditText journal;
    Button saveBtn;
    DBHelper DB;
    static final int Code = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_journals);

        DB = new DBHelper(this);

        //create hooks
        backBtn = findViewById(R.id.backButton3);
        addPhoto = findViewById(R.id.addImageButton);
        saveBtn = findViewById(R.id.SaveButton);
        journal = findViewById(R.id.editJournal);

        // go back to journaling activity
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddJournalsActivity.this, JournalingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // ask for permission to read external storage
        handlePermission();

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageChooser();

            }

        });

        // save the journals with the respective date to the database
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jText = journal.getText().toString();

                // get current date
                String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                // journal cannot be empty
                if(jText.isEmpty()){
                    Toast.makeText(AddJournalsActivity.this, "Please write something", Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean checkInsertData = DB.insertJournal(date, jText);

                    if(checkInsertData){
                        Toast.makeText(AddJournalsActivity.this, "New Journal Added", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent (AddJournalsActivity.this, JournalingActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else{
                        Toast.makeText(AddJournalsActivity.this, "No Journal Added", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void handlePermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //ask for permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    Code);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Code:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
                        if (showRationale) {
                        // Show your own message here
                        } else {
                            //User tapped never ask again
                            showSettingsAlert();
                        }
                    }
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showSettingsAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    //finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SETTINGS",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        openAppSettings(AddJournalsActivity.this);
                    }
                });
        alertDialog.show();
    }

    public static void openAppSettings(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);

    }

    /* Choose an image from Gallery */
    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), Code);
    }

    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (resultCode == RESULT_OK) {
                    if (requestCode == Code) {
                        // Get the url from data
                        final Uri selectedImageUri = data.getData();
                        if (null != selectedImageUri) {
                            // Get the path from the Uri
                            String path = getPathFromURI(selectedImageUri);
                            Log.i(TAG, "Image Path : " + path);
                            // Set the image in ImageView
                            findViewById(R.id.Image).post(new Runnable() {
                                @Override
                                public void run() {
                                    ((ImageView) findViewById(R.id.Image)).setImageURI(selectedImageUri);
                                }
                            });

                        }
                    }
                }
            }
        }).start();

    }

    /* Get the real path from the URI */
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

}