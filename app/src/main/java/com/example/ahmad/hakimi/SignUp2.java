package com.example.ahmad.hakimi;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ahmad.hakimi.basicClass.HttpRequestSender;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUp2 extends AppCompatActivity {
    Button skip,finish;
    String result_active_child,params_active_child;
    EditText ChildName,childBirth;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent1 = getIntent();
        setContentView(R.layout.activity_sign_up2);
        id=intent1.getIntExtra("id",0);
        skip=(Button)findViewById(R.id.skipBut);
        finish=(Button)findViewById(R.id.finBu);
        ChildName=(EditText) findViewById(R.id.childName);
        childBirth=(EditText)findViewById(R.id.ChildBirthDay);
    }

    public void skiBu(View view) {
        startActivity(new Intent(SignUp2.this,HomePage.class));
        finish();
    }

    public void finiBu(View view) {
        send_add_child();
    }
    public void send_add_child() {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(SignUp2.this);
                    result_active_child = http.SEND_Active2("http://188.165.159.59/testapp/dal/Login.asmx/add_childbrith",id,
                            ChildName.getText().toString(), childBirth.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("name_baby", ChildName.getText().toString());
                    jsonObject.put("brith_day", childBirth.getText().toString());
                    jsonObject.put("customerch_id", id);
                    params_active_child = jsonObject.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                if(result_active_child.contains("add succ")) {

                    // x.cancel();
                    Toast.makeText(getBaseContext(), "Child Added", Toast.LENGTH_SHORT).show();
                    // JSONObject jb = null;

                        startActivity(new Intent(SignUp2.this,HomePage.class));



                }
                else
                {
                    Toast.makeText(getBaseContext(),"Not Added",Toast.LENGTH_SHORT).show();
                    // startActivity(new Intent(getBaseContext(),MainActivity.class));
                    //  x.cancel();
                    //finish();
                }
                super.onPostExecute(aVoid);

            }
        }.execute();

    }
}
