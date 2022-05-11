package com.example.ahmad.hakimi;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmad.hakimi.basicClass.HttpRequestSender;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgetPass extends AppCompatActivity {
    EditText ResEmail;
    Button ResButton;
    TextView tvNewpass;
    String result_Rest,params_Rest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        ResEmail=(EditText)findViewById(R.id.ForgetEmail);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1D024D")));
        ResButton=(Button) findViewById(R.id.REmailBU);
        tvNewpass=(TextView) findViewById(R.id.tvNewpass);
    }

    public void restPass(View view) {
        if(isNetworkAvailable()) {
            restPass();
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
    public void restPass() {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(ForgetPass.this);
                    result_Rest = http.SEND_Forget("http://188.165.159.59/testapp/dal/Login.asmx/forget_password",
                            ResEmail.getText().toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("email", ResEmail.getText().toString());

                    params_Rest = jsonObject.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                if(result_Rest.contains("check your mail send new password")) {

                    // x.cancel();
                   tvNewpass.setText("Check your Email");
                   tvNewpass.setVisibility(View.VISIBLE);




                }
                else
                {
                    Toast.makeText(getBaseContext(),"Wrong Email",Toast.LENGTH_SHORT).show();
                    // startActivity(new Intent(getBaseContext(),MainActivity.class));
                    //  x.cancel();
                    //finish();
                }
                super.onPostExecute(aVoid);

            }
        }.execute();

    }
}
