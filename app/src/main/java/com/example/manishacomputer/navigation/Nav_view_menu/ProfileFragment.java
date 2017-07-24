package com.example.manishacomputer.navigation.Nav_view_menu;


import android.app.ProgressDialog;
import android.database.Cursor;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.manishacomputer.navigation.DbHndler.User_profile;
import com.example.manishacomputer.navigation.HttpHandler;
import com.example.manishacomputer.navigation.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private View view;
    private ImageView image;
    private TextView txt_username,txt_address,txt_email,txt_phone,txt_flag;

    public static final String url="";
    private ProgressDialog progressDialog;
    String email;
    private User_profile dbUser;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_profile, container, false);
        image = (ImageView)view.findViewById(R.id.user_image);
        txt_address=(TextView)view.findViewById(R.id.txt_address);
        txt_username=(TextView)view.findViewById(R.id.txt_username);
        txt_email=(TextView)view.findViewById(R.id.txt_email);
        txt_phone=(TextView)view.findViewById(R.id.txt_phone);
        txt_flag=(TextView)view.findViewById(R.id.txt_Flag);
        progressDialog=new ProgressDialog(getActivity());
        dbUser=new User_profile(getActivity());
        Cursor cursor=dbUser.getData();
        email=cursor.getString(cursor.getColumnIndex(dbUser.col_2));
        System.out.println("Email :"+email);
        new JSONParse().execute();
        return view;

    }

    public class JSONParse extends AsyncTask <String , Void , String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            HttpHandler sh=new HttpHandler();
            /*
            http://localhost/bloodbank/user_profile.php?Email=roshan@gmail.com
             */
            String jsonStr=sh.makeServiceCall("http://192.168.137.1/bloodbank/user_profile.php?Email="+email);
            System.out.println("JSonparse :"+jsonStr);
            return jsonStr;
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            super.onPostExecute(jsonStr);
            if (progressDialog.isShowing()){
                progressDialog.dismiss();

                try {
                    JSONArray jsonArray=new JSONArray(jsonStr);
                    for (int i=0; i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String username=jsonObject.optString("Username");
                        txt_username.setText(username);
                        System.out.println("USername :"+username);
                        String Email=jsonObject.optString("Email");
                        txt_email.setText(Email);
                        String Phone=jsonObject.optString("Phone");
                        txt_phone.setText(Phone);
                        String Address=jsonObject.optString("Address");
                        txt_address.setText(Address);

                        //image loading
                        String Image="http://192.168.137.1/bloodbank/Image/" + jsonObject.optString("Image");
                        Picasso.with(getActivity()).load(Image).into(image);


                        String Flag=jsonObject.optString("Flag");
                        if (Flag.equals("0")){
                            txt_flag.setText("I am not a donor");
                        }if (Flag.equals("1")){
                            txt_flag.setText("I am a donor");
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
