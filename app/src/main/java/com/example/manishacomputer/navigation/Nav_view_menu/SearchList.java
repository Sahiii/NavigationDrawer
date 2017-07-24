package com.example.manishacomputer.navigation.Nav_view_menu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.example.manishacomputer.navigation.DbHndler.User_profile;
import com.example.manishacomputer.navigation.DiscipsionActivity;
import com.example.manishacomputer.navigation.HttpHandler;
import com.example.manishacomputer.navigation.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchList extends Fragment {

    private View view;
    private GridView listView;
    private User_profile dbSearch;

    //private ArrayList<Blood_detail> blood_details;
    ArrayList<HashMap<String, String>> blood_details;
    // URL to get contacts JSON
    private static String url = "http://192.168.137.1/bloodbank/api.php";
    private ProgressDialog progressDialog;

    public SearchList
            () {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_bloodbank, container, false);
        listView=(GridView) view.findViewById(R.id.listview);
        blood_details=new ArrayList<>();
        dbSearch=new User_profile(getActivity());
        progressDialog=new ProgressDialog(getActivity());
        new JsonTask().execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String ,String> blood_user=blood_details.get(position);
                Intent intent=new Intent(getActivity(), DiscipsionActivity.class);
                intent.putExtra("Username",blood_user.get("username"));
                intent.putExtra("Email",blood_user.get("email"));
                intent.putExtra("Phone",blood_user.get("phone"));
                intent.putExtra("Address",blood_user.get("Address"));
                startActivity(intent);
            }
        });

        return view;
    }

    public class JsonTask extends AsyncTask<String ,Void,String > {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Loading");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpHandler httpHandler=new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = httpHandler.makeServiceCall(url);
            System.out.println("String :"+jsonStr);

            try {
                JSONArray array=new JSONArray(jsonStr);

                for (int i = 0; i < array.length(); i++){
                    JSONObject jsonObject=array.getJSONObject(i);
                    // JSONObject c=array.getJSONObject(i);
                    String Username=jsonObject.optString("Username");
                    String Email=jsonObject.optString("Email");
                    String Password=jsonObject.optString("Password");
                    String Phone=jsonObject.optString("Phone");
                    String Address=jsonObject.optString("Address");
                    String Gender=jsonObject.optString("Gender");
                    String blood_group=jsonObject.optString("blood_group");
                    String Image=jsonObject.optString("Image");
                    String Flag=jsonObject.optString("Flag");
                    dbSearch.insertData1(blood_group,Username,Phone);


                    System.out.println("Username :"+Username);
                    System.out.println("Email:"+Email);
                    System.out.println("Password:"+Password);
                    System.out.println("Phone:"+Phone);
                    System.out.println("Address:"+Address);
                    System.out.println("Image:"+Image);
                    System.out.println("Flag:"+Flag);
                    //get sinlge user detail
                    HashMap<String ,String> blood_user=new HashMap<>();

                    //getinh all information

                    blood_user.put("username",Username);
                    blood_user.put("email",Email);
                    blood_user.put("phone",Phone);
                    blood_user.put("Address",Address);
                    blood_user.put("Gender",Gender);
                    blood_user.put("Flag",Flag);
                    blood_user.put("blood_group",blood_group);
                 
                    blood_details.add(blood_user);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return jsonStr;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (progressDialog.isShowing())
                progressDialog.dismiss();

            ListAdapter listAdapter=new SimpleAdapter(getActivity(),blood_details,R.layout.fragment_searchlist,new String[]{"username","email","phone","Address","Gender","Flag"},new int[]{R.id.txt_username,R.id.txt_email,R.id.txt_phone,R.id.txt_address,R.id.txt_Gender,R.id.txt_Flag});
            listView.setAdapter(listAdapter);
        }
    }

}
