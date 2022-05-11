package com.example.ahmad.hakimi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.Toast;

import com.example.ahmad.hakimi.basicClass.HttpRequestSender;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUp extends AppCompatActivity implements AdapterView.OnItemClickListener {
    String result_reg, params_reg;
    EditText mail,pass,phone,addr,birth,gend;
    Button registrationBu ;
    ListPopupWindow listPopupWindow;
    String[] products={"Male","Famale"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mail=(EditText)findViewById(R.id.rmail);
        pass=(EditText)findViewById(R.id.rpass);
        phone=(EditText)findViewById(R.id.rphone);

        addr=(EditText)findViewById(R.id.raddress);
        birth=(EditText)findViewById(R.id.rbirth);

        gend=(EditText)findViewById(R.id.rgender);

        registrationBu=(Button)findViewById(R.id.registerBu);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1D024D")));
        listPopupWindow = new ListPopupWindow(
                SignUp.this);
        listPopupWindow.setAdapter(new ArrayAdapter(
                SignUp.this,
                R.layout.list_item, products));
        listPopupWindow.setAnchorView(gend);
        listPopupWindow.setWidth(300);
        listPopupWindow.setHeight(400);
        listPopupWindow.setModal(true);
        listPopupWindow.setOnItemClickListener((AdapterView.OnItemClickListener) SignUp.this);

        gend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listPopupWindow.show();
            }
        });

    }
    public void sign_UP(View view) {
        if(isNetworkAvailable()) {
            sendReg();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
    public void sendReg() {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(SignUp.this);
                    result_reg = http.SEND_REG("http://188.165.159.59/testapp/dal/Login.asmx/add_customer_reg",
                            mail.getText().toString(),pass.getText().toString(),phone.getText().toString(),"",
                            addr.getText().toString(),birth.getText().toString(),"",gend.getText().toString(),
                            "");
                    //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                    //      +username.getText().toString()+"&password="+password1.getText().toString());
                    Log.d("String", result_reg);


                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                JSONObject jsonObject = new JSONObject();


                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                if(result_reg.contains("add succ")) {
                  //  x.cancel();
                 //   Toast.makeText(getBaseContext(), "hiii", Toast.LENGTH_SHORT).show();
                    JSONObject jb = null;
                    try {
                       jb = new JSONObject(result_reg);
                        //   int id= (int) jb.get("id\\");
                       // String mes= (String) jb.get("d");
                      //  Toast.makeText(getBaseContext(),  "  " + mes, Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getBaseContext(),ActiveEmail.class));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                if(result_reg.contains("exist")) {

                    JSONObject jb = null;
                    try {
                        jb = new JSONObject(result_reg);
                        //   int id= (int) jb.get("id\\");
                        //String mes= (String) jb.get("d");
                        Toast.makeText(getBaseContext(),  " this user Exist " , Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                super.onPostExecute(aVoid);

            }
        }.execute();

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        gend.setText(products[position]);
        listPopupWindow.dismiss();


    }
}
