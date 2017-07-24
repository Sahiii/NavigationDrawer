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
import android.widget.Button;
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
public class HomeFragment extends Fragment {
private View view;
    private ImageView image;
    private TextView username,address,bloodgroup,textunit;
    Button btnsaveblood,btntransfer;
    private ProgressDialog progressDialog;
    String email;
    private User_profile dbUser;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_home, container, false);
        progressDialog=new ProgressDialog(getActivity());
        dbUser=new User_profile(getActivity());
        image=(ImageView)view.findViewById(R.id.user_image);
        username=(TextView) view.findViewById(R.id.txt_username);
        address=(TextView) view.findViewById(R.id.txt_address);
        bloodgroup=(TextView)view.findViewById(R.id.txt_Blood_group);
        textunit=(TextView) view.findViewById(R.id.txt_unit);
        btnsaveblood=(Button) view.findViewById(R.id.txt_make_save_blood);
        btntransfer=(Button) view.findViewById(R.id.txt_make_transfer);
        try {
            Cursor cursor=dbUser.getData();
            email=cursor.getString(cursor.getColumnIndex(dbUser.col_2));
        }catch (NullPointerException ex){
            ex.printStackTrace();
        }

        new JSONParse().execute();
        return view;
    }
    public class JSONParse extends AsyncTask<String , Void , String> {

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
                        String username1=jsonObject.optString("Username");
                        username.setText(username1);
                        System.out.println("USername :"+username);

                        String blood_group1=jsonObject.optString("blood_group");
                        bloodgroup.setText(blood_group1);
                        String Address1=jsonObject.optString("Address");
                        address.setText(Address1);
                        String blood_unit=jsonObject.optString("blood_unit");
                        textunit.setText(blood_unit);

                        //image loading
                        String Image="http://192.168.137.1/bloodbank/Image/" + jsonObject.optString("Image");
                        Picasso.with(getActivity()).load(Image).into(image);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
