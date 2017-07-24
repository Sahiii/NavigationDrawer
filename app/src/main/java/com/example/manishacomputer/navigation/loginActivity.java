package com.example.manishacomputer.navigation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manishacomputer.navigation.DbHndler.User_profile;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class loginActivity extends AppCompatActivity {
    private Button btn_login;
    //   private ImageView image;
    private EditText  edit_email, edit_pass;
    private TextView txt_sigin_up;
    private User_profile dbProfile;

    String email;

    String password;

    ProgressDialog progressDialog;
    private String register_url = "http://192.168.137.1/bloodbank/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbProfile=new User_profile(loginActivity.this);
        btn_login = (Button) findViewById(R.id.btn_login1);
//        edit_address = (EditText) findViewById(R.id.edit_address);
        edit_email = (EditText) findViewById(R.id.edit_email);
        // edit_phone = (EditText) findViewById(R.id.edit_phone);
        edit_pass = (EditText) findViewById(R.id.edit_password);
        txt_sigin_up = (TextView) findViewById(R.id.txt_no_account);
        //   rb_female = (RadioButton) findViewById(R.id.rb_female);
        // rb_male = (RadioButton) findViewById(R.id.rb_male);
        //checkBox_donor=(CheckBox)findViewById(R.id.checkbox_donor);
        //image = (ImageView) findViewById(R.id.user_image);
        progressDialog=new ProgressDialog(loginActivity.this);
        try {
            Cursor cr=dbProfile.getData();
            if (cr.getCount()>0){
                startActivity(new Intent(loginActivity.this,NavigationActivity.class));
            }
        }catch (NullPointerException ex){
            ex.printStackTrace();
        }catch (CursorIndexOutOfBoundsException e){
            e.printStackTrace();
        }
//        image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent, RQS_IMAGE1);
//            }
//        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        txt_sigin_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginActivity.this,RegisterActivity.class));
            }
        });
    }

    private void login() {
        email = edit_email.getText().toString();
        password = edit_pass.getText().toString();
//        addess = edit_address.getText().toString();
//        phone = edit_phone.getText().toString();
//        username = edit_name.getText().toString();
//        if (rb_female.isChecked()) {
//            gender = "female";
//        }
//        if (rb_male.isChecked()) {
//            gender = "male";
//        }
//        if (checkBox_donor.isChecked()){
//            flag="1";
//        }else {
//            flag="0";
//        }
        if (email.equals("") || password.equals("")) {
            Toast.makeText(this, "Some field is missing", Toast.LENGTH_SHORT).show();

        } else {
            //media type


            new GetDetail().execute();
        }
    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                case RQS_IMAGE1:
//                    source1 = data.getData();
//                    try {
////                        System.out.println("Bitmap path = "+source1.getPath());
//                        Bitmap bm1 = BitmapFactory.decodeStream(
//                                getContentResolver().openInputStream(source1));
//                        image.setImageBitmap(bm1);
//                        System.out.println("IMagePAth: " + source1.getPath());
//
//                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                        Cursor cursor = getContentResolver().query(source1, filePathColumn, null, null, null);
//                        cursor.moveToFirst();
//                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                        String filePath = cursor.getString(columnIndex);
//                        cursor.close();
//                        file = new File(filePath);
////
//                        System.out.println("Image :" + file);
//                    } catch (NullPointerException e) {
//                        e.printStackTrace();
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//
//                    break;
//            }
//        }
//    }

    public class GetDetail extends AsyncTask<Void, Integer, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //   progressDialog=new ProgressDialog(getApplicationContext());
            progressDialog.setMessage("Login");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                return uploadFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() throws IOException {
            String responseString = null;
            Response responses = null;

            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    //.addFormDataPart("Username", username)
                    .addFormDataPart("Email",email)
                    .addFormDataPart("Password",password)
                    //  .addFormDataPart("Phone",phone)
                    //  .addFormDataPart("Address",addess)
                    //  .addFormDataPart("Gender",gender)
                    // .addFormDataPart("Image",file.getName(),RequestBody.create(MediaType.parse("image/png"),file))
                    // .addFormDataPart("Flag", flag)
                    .build();

            okhttp3.Request request = new okhttp3.Request.Builder().url(register_url).post(formBody).build();
            try {
                responses = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String jsondata=responses.body().string();
            System.out.println("Json Data"+jsondata);

            return jsondata;

        }

        @Override
        protected void onPostExecute(String jsondata) {

            super.onPostExecute(jsondata);
            System.out.println("String :" + jsondata);
            progressDialog.dismiss();
            try {
                JSONObject jsonObject=new JSONObject(jsondata);
                String code=jsonObject.getString("success");
                System.out.println("Code :"+code);
                if (code.equals("1")){
                    Toast.makeText(loginActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                    dbProfile.insertData(email,password);
                    startActivity(new Intent(getApplicationContext(),NavigationActivity.class));
                }if (code.equals("0")){
                    Toast.makeText(loginActivity.this, "Not Login", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}