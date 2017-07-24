package com.example.manishacomputer.navigation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    MediaPlayer mysound;
    private Button btn_register;
    private ImageView image;
    private EditText edit_name, edit_email, edit_pass, edit_phone, edit_address;
    private RadioButton rb_male, rb_female,rb_aplus,rb_aminus,rb_bplus,rb_bminus,rb_abplus,rb_abminus,rb_oplus,rb_ominus;
    private CheckBox checkBox_donor;
    private TextView txt_already;
    final int RQS_IMAGE1 = 1;
    Uri source1;
    File file;
    String email;
    String username;
    String password;
    String phone;
    String addess;
    String gender;
    String blood_group;
    String blood_unit;
    String flag;
    private User_profile dbProfile;
    ProgressDialog progressDialog;
    private String register_url = "http://192.168.137.1/bloodbank/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mysound=MediaPlayer.create(this,R.raw.sleep);
        rb_abminus=(RadioButton)findViewById(R.id.rb_abminus);
        rb_abplus=(RadioButton)findViewById(R.id.rb_abplus);
        rb_ominus=(RadioButton)findViewById(R.id.rb_ominus);
        rb_oplus=(RadioButton)findViewById(R.id.rb_oplus);
        rb_aminus=(RadioButton)findViewById(R.id.rb_aminus);
        rb_aplus=(RadioButton)findViewById(R.id.rb_aplus);
        rb_bminus=(RadioButton)findViewById(R.id.rb_bminus);
        rb_bplus=(RadioButton)findViewById(R.id.rb_bplus);
        btn_register = (Button) findViewById(R.id.btn_register);
        edit_address = (EditText) findViewById(R.id.edit_address);
        edit_email = (EditText) findViewById(R.id.edit_email);
        edit_phone = (EditText) findViewById(R.id.edit_phone);
        edit_pass = (EditText) findViewById(R.id.edit_password);
        edit_name = (EditText) findViewById(R.id.edit_username);
        txt_already = (TextView) findViewById(R.id.txt_already);
        rb_female = (RadioButton) findViewById(R.id.rb_female);
        rb_male = (RadioButton) findViewById(R.id.rb_male);
        checkBox_donor=(CheckBox)findViewById(R.id.check_donor);
        image = (ImageView) findViewById(R.id.user_image);
        progressDialog=new ProgressDialog(RegisterActivity.this);
        dbProfile=new User_profile(RegisterActivity.this);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RQS_IMAGE1);
            }
        });

        try {
            Cursor cr=dbProfile.getData();
            if (cr.getCount()>0){
                startActivity(new Intent(RegisterActivity.this,NavigationActivity.class));
            }
        }catch (NullPointerException ex){
            ex.printStackTrace();
        }catch (CursorIndexOutOfBoundsException e){
            e.printStackTrace();
        }



        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();

            }
        });

        txt_already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,loginActivity.class));
            }
        });
    }
//    public void play(View view){
//        mysound.start();
//    }


    private void register() {
        email = edit_email.getText().toString();
        password = edit_pass.getText().toString();
        addess = edit_address.getText().toString();
        phone = edit_phone.getText().toString();
        username = edit_name.getText().toString();
        if (rb_female.isChecked()) {
            gender = "female";
        }
        if (rb_male.isChecked()) {
            gender = "male";
        }
        if (rb_abplus.isChecked()){
            blood_group="AB+";
        }
        if (rb_abminus.isChecked()){
            blood_group="AB-";
        }
        if (rb_aminus.isChecked()){
            blood_group="A-";
        }
        if (rb_aplus.isChecked()){
            blood_group="A+";
        }
        if (rb_bplus.isChecked()){
            blood_group="B+";
        }
        if (rb_bminus.isChecked()){
            blood_group="B-";
        }
        if (rb_ominus.isChecked()){
            blood_group="O-";
        }
        if (rb_oplus.isChecked()){
            blood_group="O+";
        }
        if (checkBox_donor.isChecked()){
            flag="1";
        }else {
            flag="0";
        }
        if (email.equals("") || password.equals("") || addess.equals("") || phone.equals("") || username.equals("") || gender.equals("")) {
            Toast.makeText(this, "Some field is missing", Toast.LENGTH_SHORT).show();

        } else {
            //media type

            mysound.start();

            new GetDetail().execute();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RQS_IMAGE1:
                    source1 = data.getData();
                    try {
//                        System.out.println("Bitmap path = "+source1.getPath());
                        Bitmap bm1 = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(source1));
                        image.setImageBitmap(bm1);
                        System.out.println("IMagePAth: " + source1.getPath());

                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(source1, filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String filePath = cursor.getString(columnIndex);
                        cursor.close();
                        file = new File(filePath);
//
                        System.out.println("Image :" + file);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    break;
            }
        }
    }

    public class GetDetail extends AsyncTask<Void, Integer, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
         //   progressDialog=new ProgressDialog(getApplicationContext());
            progressDialog.setMessage("Registering");
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
                    .setType(MultipartBody.FORM).addFormDataPart("Username", username)
                    .addFormDataPart("Email",email)
                    .addFormDataPart("Password",password)
                    .addFormDataPart("Phone",phone)
                    .addFormDataPart("Address",addess)
                    .addFormDataPart("Gender",gender)
                     .addFormDataPart("Image",file.getName(),RequestBody.create(MediaType.parse("image/png"),file))
                    .addFormDataPart("Flag", flag)
                    .addFormDataPart("blood_group",blood_group)
                    .addFormDataPart("blood_unit","00")
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
                    if (code.equals("1")){
                        Toast.makeText(RegisterActivity.this, "Register", Toast.LENGTH_SHORT).show();
                        dbProfile.insertData(email,password);
                        System.out.println("Inserting :");
                        finish();
                        startActivity(new Intent(getApplicationContext(),NavigationActivity.class));
                        if (mysound.isPlaying()){
                            mysound.pause();
                        }
                    }if (code.equals("0")){
                        Toast.makeText(RegisterActivity.this, "Not register", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

        }
    }

}