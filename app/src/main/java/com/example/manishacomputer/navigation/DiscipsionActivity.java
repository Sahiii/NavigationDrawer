package com.example.manishacomputer.navigation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DiscipsionActivity extends AppCompatActivity {
private TextView name,email,address,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discipsion);
        name=(TextView)findViewById(R.id.name);
        email=(TextView)findViewById(R.id.email);
        address=(TextView)findViewById(R.id.address);
        phone=(TextView)findViewById(R.id.phone);
        String name1= getIntent().getExtras().getString("Username");
        String email1=getIntent().getExtras().getString("Email");
        String pone1=getIntent().getExtras().getString("Phone");
        String address1=getIntent().getExtras().getString("Address");
        email.setText(email1);
        phone.setText(pone1);
        address.setText(address1);
        name.setText(name1);
        DiscipsionActivity.this.setTitle(name1);
    }
}
