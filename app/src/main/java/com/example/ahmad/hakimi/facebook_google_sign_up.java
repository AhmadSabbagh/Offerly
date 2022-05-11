package com.example.ahmad.hakimi;

import android.app.ProgressDialog;
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

import com.example.ahmad.hakimi.basicClass.AttolSharedPreference;
import com.example.ahmad.hakimi.basicClass.HttpRequestSender;

import org.json.JSONException;
import org.json.JSONObject;


public class facebook_google_sign_up extends AppCompatActivity implements AdapterView.OnItemClickListener {
EditText facePhone,faceAge,faceGender;
Button faceBU;
String FaceEmail,GoogleEmail,result_reg_face,params_reg_face;
    ProgressDialog x;
    ListPopupWindow listPopupWindow;
    String[] products={"Male","Famale"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_google_sign_up);
        Intent intent2=getIntent();
        GoogleEmail=intent2.getStringExtra("email");
        FaceEmail = intent2.getStringExtra("Femail");
        facePhone=(EditText)findViewById(R.id.facephoneID);
        faceAge=(EditText)findViewById(R.id.faceAgeID);
        faceGender=(EditText)findViewById(R.id.faceGenderID);
        faceBU=(Button)findViewById(R.id.faceButtonID);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1D024D")));

        listPopupWindow = new ListPopupWindow(
                facebook_google_sign_up.this);
        listPopupWindow.setAdapter(new ArrayAdapter(
                facebook_google_sign_up.this,
                R.layout.list_item, products));
        listPopupWindow.setAnchorView(faceGender);
        listPopupWindow.setWidth(300);
        listPopupWindow.setHeight(400);
        listPopupWindow.setModal(true);
        listPopupWindow.setOnItemClickListener((AdapterView.OnItemClickListener) facebook_google_sign_up.this);

        faceGender.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listPopupWindow.show();
            }
        });
    }

    public void faceBuClick(View view) {
        if(isNetworkAvailable()) {
            sendReg();
        }
    }
    public void sendReg() {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(facebook_google_sign_up.this);
                    result_reg_face = http.SEND_REG_face("http://188.165.159.59/testapp/dal/Login.asmx/add_customer_reg",
                            FaceEmail,GoogleEmail,facePhone.getText().toString(),faceGender.getText().toString(), faceAge.getText().toString(),
                            "","","","");

                    //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                    //      +username.getText().toString()+"&password="+password1.getText().toString());
                    Log.d("String", result_reg_face);


                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                JSONObject jsonObject = new JSONObject();
                try {

                    if(FaceEmail!=null) {
                        jsonObject.put("google  ", FaceEmail);
                    }
                    if(GoogleEmail!=null) {
                        jsonObject.put("facebook  ", GoogleEmail);
                    }
                    jsonObject.put("email", "");
                    jsonObject.put("password", "face/google_pass");
                    jsonObject.put("phone  ",facePhone.getText().toString());
                    jsonObject.put("nationality", "facebook/google");
                    jsonObject.put("address", "facebook/google");
                    jsonObject.put("brith_day", "facebook/google");
                    jsonObject.put("marriage_date", "facebook/google");
                    jsonObject.put("gender", faceGender.getText().toString());
                    jsonObject.put("age", Integer.parseInt(faceAge.getText().toString()));
                    jsonObject.put("network_type", "orange");
                    jsonObject.put("device_type", "android");


                    params_reg_face = jsonObject.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                if(result_reg_face.contains("succ")) {
                    //  x.cancel();
                //    Toast.makeText(getBaseContext(), "hiii", Toast.LENGTH_SHORT).show();
                    JSONObject jb = null;
                    try {
                        jb = new JSONObject(result_reg_face);
                        //   int id= (int) jb.get("id\\");
                        //String mes= (String) jb.get("d");
                      //  Toast.makeText(getBaseContext(),  "  " + mes, Toast.LENGTH_SHORT).show();
                        sendRequest();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                if(result_reg_face.contains("exist")) {

                    JSONObject jb = null;
                    try {
                        jb = new JSONObject(result_reg_face);
                        //   int id= (int) jb.get("id\\");
                       // String mes= (String) jb.get("d");
                       // Toast.makeText(getBaseContext(),  "exist " , Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    public void sendRequest() {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(facebook_google_sign_up.this);
                    if(FaceEmail!=null) {
                        result_reg_face = http.SEND_POST1("http://188.165.159.59/testapp/dal/Login.asmx/Login", FaceEmail, "123");
                    }
                    else if (GoogleEmail!=null)
                    {
                        result_reg_face = http.SEND_POST1("http://188.165.159.59/testapp/dal/Login.asmx/Login", GoogleEmail, "123");
                    }
                    //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                    //      +username.getText().toString()+"&password="+password1.getText().toString());
                    if (result_reg_face.contains("id")) {
                        String ID = new JSONObject(result_reg_face).getString("id");
                        http.setID(ID);
                        Log.d("String", result_reg_face);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                JSONObject jsonObject = new JSONObject();
                try {
                    if(FaceEmail!=null) {
                        jsonObject.put("email  ", FaceEmail);
                    }
                    if(GoogleEmail!=null) {
                        jsonObject.put("email  ", GoogleEmail);
                    }
                    jsonObject.put("password", "123");

                    params_reg_face = jsonObject.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                if(result_reg_face.contains("login succ")) {


                 //   Toast.makeText(getBaseContext(), "Welcome", Toast.LENGTH_SHORT).show();
                    // JSONObject jb = null;
                    try {
                        String ID = new JSONObject(result_reg_face).getString("id");
                        //String accessToken1 = new JSONObject(result).getString("email");
                        AttolSharedPreference mySharedPreference = new AttolSharedPreference(facebook_google_sign_up.this);
                        mySharedPreference.setKey("id", ID);

                        startActivity(new Intent(getBaseContext(),First_Page.class));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                    Toast.makeText(getBaseContext(),"INVALID USER NAME OR PASSWORD",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(),MainActivity.class));
                    x.cancel();
                    finish();
                }
                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    private void show() {

        x.setMessage("Loging...");
        x.setCancelable(false);
        x.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        faceGender.setText(products[position]);
        listPopupWindow.dismiss();
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

}
