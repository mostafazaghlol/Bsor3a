package com.mostafa.android.bsor3a.LoginAndRegister;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.mostafa.android.bsor3a.MainActivity;
import com.mostafa.android.bsor3a.R;
import com.mostafa.android.bsor3a.setBar;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.Register)
    Button ButtonRegister;
    @BindView(R.id.userImage)
    ImageView userImage;
    @BindView(R.id.fullname)
    EditText EdfullName;
    @BindView(R.id.phone)
    EditText Edphone;
    @BindView(R.id.email)
    EditText Edemail;
    @BindView(R.id.shortname)
    EditText Edshortname;
    @BindView(R.id.password)
    EditText Edpassword;
    @BindView(R.id.CBAccecpt)
    CheckBox CBAccecpt;
    private int CAMERA_REQUEST = 188;
    private int PICK_IMAGE = 100;
    String encodimg = "0";
    Uri imageUri;
    ProgressDialog progDailog;
    String name, phone, message = "Noting", state_id, email, nickname, street_name, building_number, flower_number, password, urluser = "https://bsor3a.com/clients/register", result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setBar.setStatusBarColored(this);
        ButterKnife.bind(this);
        final CharSequence[] items = {getString(R.string.choosePic), getString(R.string.picfromstudio), getString(R.string.cancel)};
        //get Camera Request
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, CAMERA_REQUEST);
        }
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImage(items);
            }
        });
        ButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });

    }

    private void sendData() {
        intialize();
        if (!validate()) {

        } else {
            onRegisterSuccess();
        }
    }

    public void onRegisterSuccess() {
        processing();
        new GetDateUser().execute(name, phone, email, nickname, password, encodimg);

    }

    public void processing() {
        progDailog = new ProgressDialog(RegisterActivity.this);
        progDailog.setTitle(getString(R.string.UploadData));
        progDailog.setMessage(getString(R.string.pleasewait));
        progDailog.setProgress(0);
        progDailog.setMax(70);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int progress = 0;
                while (progress <= 70) {
                    try {
                        progDailog.setProgress(progress);
                        progress++;
                        Thread.sleep(700);
                    } catch (Exception e) {

                    }
                }
                progDailog.dismiss();
            }
        });
        thread.start();
        progDailog.show();
    }

    private void intialize() {
        name = getTextFromEditText(EdfullName, R.string.enterName);
        phone = getTextFromEditText(Edphone, R.string.enterPhone);
        email = getTextFromEditText(Edemail, R.string.enterEmail);
        nickname = getTextFromEditText(Edshortname, R.string.enterShortName);
        password = getTextFromEditText(Edpassword, R.string.enterPassword);
    }

    public String getTextFromEditText(EditText editText, int id) {

        if (editText.getText().toString().isEmpty()) {
            editText.setError(getString(id));
        } else {
            return editText.getText().toString();
        }
        return "";
    }

    public boolean validate() {
        boolean valid = true;
        if (encodimg == "0") {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage(getString(R.string.choosePicture))
                    .setNegativeButton(getString(R.string.yes), null)
                    .create()
                    .show();
            valid = false;
        }
        return valid;
    }

    private void getImage(final CharSequence[] items) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle(getString(R.string.choosePicture));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals(getString(R.string.choosePic))) {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, CAMERA_REQUEST);
                } else if (items[i].equals(getString(R.string.picfromstudio))) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, PICK_IMAGE);
                } else if (items[i].equals(getString(R.string.cancel))) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            userImage.setImageBitmap(photo);
            Bitmap Bimg = ((BitmapDrawable) userImage.getDrawable()).getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Bimg.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            encodimg = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            // Log.d("The image",encodimg.toString());

        }
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            userImage.setImageURI(imageUri);
            Bitmap Bimg = ((BitmapDrawable) userImage.getDrawable()).getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Bimg.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            encodimg = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            // Log.d("The image",encodimg.toString());

        }
    }

    public void back(View view) {
        onBackPressed();
    }

    class GetDateUser extends AsyncTask<String, Boolean, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            String name = strings[0];
            String phone = strings[1];
            String email = strings[2];
            String nickname = strings[3];
            String password = strings[4];
            String img = strings[5];
            String streetName = "hi";
            String buildingNumber = "q0";
            String flowerNumber = "10";
            String state_id = "1";
            // if  you have  to send  data  to the databse
            ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("lang", String.valueOf(MainActivity.lang)));
            pairs.add(new BasicNameValuePair("name", "mostafa"));
            pairs.add(new BasicNameValuePair("phone", "0101255552"));
            pairs.add(new BasicNameValuePair("state_id", "10"));
            pairs.add(new BasicNameValuePair("email", "mostafas.zaghlol@gmail.com"));
            pairs.add(new BasicNameValuePair("nickname", "sasa"));
            pairs.add(new BasicNameValuePair("street_name", "buildingNumber"));
            pairs.add(new BasicNameValuePair("building_number", "10"));
            pairs.add(new BasicNameValuePair("flower_number", "22"));
            pairs.add(new BasicNameValuePair("password", "100000000"));
            pairs.add(new BasicNameValuePair("img", img));


            com.mostafa.android.bsor3a.LoginAndRegister.JsonReader j = new com.mostafa.android.bsor3a.LoginAndRegister.JsonReader(urluser, pairs);
            result = j.sendRequest();

            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("message");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonobject = jsonArray.getJSONObject(i);
                    message = jsonobject.getString("message");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            progDailog.cancel();
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage(message)
                    .setNegativeButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            end();
                        }
                    })
                    .create()
                    .show();

        }
    }

    public void end() {
        finish();
    }

}
