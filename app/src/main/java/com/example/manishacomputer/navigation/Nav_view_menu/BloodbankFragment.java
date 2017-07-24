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
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.manishacomputer.navigation.Blood_detail;
import com.example.manishacomputer.navigation.DiscipsionActivity;
import com.example.manishacomputer.navigation.HttpHandler;
import com.example.manishacomputer.navigation.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BloodbankFragment extends Fragment {

private View view;
    private ListView listView;
    //private ArrayList<Blood_detail> blood_details;
    ArrayList<HashMap<String, String>> blood_details;
    // URL to get contacts JSON
    private static String url = "http://192.168.137.1/bloodbank/blood_group.php";
    private ProgressDialog progressDialog;

    public BloodbankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.list_detail, container, false);
        listView=(ListView) view.findViewById(R.id.listView);
        blood_details=new ArrayList<>();
        progressDialog=new ProgressDialog(getActivity());
        new JsonTask().execute();

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                HashMap<String ,String> blood_user=blood_details.get(position);
//                Intent intent=new Intent(getActivity(), DiscipsionActivity.class);
//                intent.putExtra("Username",blood_user.get("username"));
//                intent.putExtra("Email",blood_user.get("email"));
//                intent.putExtra("Phone",blood_user.get("phone"));
//                intent.putExtra("Address",blood_user.get("Address"));
//                startActivity(intent);
//            }
//        });

        return view;
    }

    public class JsonTask extends AsyncTask<String ,Void,String >{

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
                    String Blood_Group=jsonObject.optString("Blood_Group");
                    String Blood_unit=jsonObject.optString("Blood_unit");

                    System.out.println("bg "+Blood_Group);
                    System.out.println("bu "+Blood_unit);

                    //get sinlge user detail
                    HashMap<String ,String> blood_user=new HashMap<>();

                    //getinh all information

                    blood_user.put("blood",Blood_Group);
                    blood_user.put("Blood_unit",Blood_unit);

                    // adding student_info to student list
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

            ListAdapter listAdapter=new SimpleAdapter(getActivity(),blood_details,R.layout.blood_list_detail,new String[]{"blood","Blood_unit"},new int[]{R.id.tx_blood_group,R.id.txt_blood_unit});
            listView.setAdapter(listAdapter);
        }
    }

}
