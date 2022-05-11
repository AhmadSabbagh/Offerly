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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ahmad.hakimi.basicClass.AttolSharedPreference;
import com.example.ahmad.hakimi.basicClass.HttpRequestSender;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgetPasswordPage extends AppCompatActivity {
EditText forgetEmail,forgetOldPass,forgetNewPass;
String result_forget , params_forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_page);
        forgetEmail =(EditText)findViewById(R.id.ForgerEmailId);
        forgetOldPass=(EditText)findViewById(R.id.ForgetOldPassId);
        forgetNewPass=(EditText)findViewById(R.id.ForgetNewPassId);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1D024D")));

    }

    public void ForgerBuId(View view) {
        if(isNetworkAvailable()) {
            forget();
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
    public void forget() {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(ForgetPasswordPage.this);
                    result_forget = http.SEND_POST_forget_password("http://188.165.159.59/testapp/dal/Login.asmx/change_password", forgetEmail.getText().toString(), forgetOldPass.getText().toString(), forgetNewPass.getText().toString());
                    //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                    //      +username.getText().toString()+"&password="+password1.getText().toString());


                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("email", forgetEmail.getText().toString());
                    jsonObject.put("old_password", forgetOldPass.getText().toString());
                    jsonObject.put("new_password", forgetNewPass.getText().toString());
                    params_forget = jsonObject.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                if(result_forget.contains("message")) {


                    Toast.makeText(getBaseContext(), "Password Changed", Toast.LENGTH_SHORT).show();
                    // JSONObject jb = null;

                        startActivity(new Intent(getBaseContext(),First_Page.class));
                        finish();




                }
                else
                {
                    Toast.makeText(getBaseContext(),"INVALID information",Toast.LENGTH_SHORT).show();

                }
                super.onPostExecute(aVoid);

            }
        }.execute();

    }
}
