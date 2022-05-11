package com.example.ahmad.hakimi;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ahmad.hakimi.basicClass.HttpRequestSender;

public class OrderActivity extends AppCompatActivity {
int product_id;
String result_product;
EditText name,phone,address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Intent intent=getIntent();
        product_id=intent.getIntExtra("product_id",0);
        name=(EditText)findViewById(R.id.NAMEO);
        phone=(EditText)findViewById(R.id.PHONEO);
        address=(EditText)findViewById(R.id.ADDRESSO);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1D024D")));

    }
    public void MakeOrder(final int product_id) {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(OrderActivity.this);
                    result_product = http.SEND_POST_order("http://188.165.159.59/testapp/dal/Login.asmx/add_product_order",
                           name.getText().toString(),phone.getText().toString(),address.getText().toString() ,product_id);
                    //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                    //      +username.getText().toString()+"&password="+password1.getText().toString());

                    Log.d("String", result_product);


                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {



                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                if(result_product.contains("succ")) {

                    Toast.makeText(OrderActivity.this,"Your Order Received",Toast.LENGTH_SHORT).show();


                }
                else
                {
                    Toast.makeText(OrderActivity.this,"Please Try Again",Toast.LENGTH_SHORT).show();

                }
                super.onPostExecute(aVoid);

            }
        }.execute();

    }

    public void orderSend(View view) {
        MakeOrder(product_id);
        finish();
    }
}
