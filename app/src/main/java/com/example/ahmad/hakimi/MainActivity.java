package com.example.ahmad.hakimi;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ahmad.hakimi.basicClass.AttolSharedPreference;
import com.example.ahmad.hakimi.basicClass.HttpRequestSender;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener {
    EditText username;
    EditText password1;
    TextView tvIsConnected;
    Button loginButton1;
    String result,params,result_check,params_check;
    ProgressDialog x;
    CallbackManager callbackManager;
    LoginButton loginButton;
    Boolean isEnable=false;

    ///////////////////////////////////////////// Google part
    GoogleSignInButton signInButton;
    GoogleApiClient googleApiClient;
    public static final int REQ_CODE=9001;
    ///////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //////////////////////////////////////////////////////////////////
        AttolSharedPreference attolSharedPreference = new AttolSharedPreference(this);
        String id = attolSharedPreference.getKey("id");
       if (id==null)
        {

        }
       else if (id.equals("0"))
        {
           // startActivity(new Intent(MainActivity.this,First_Page.class));
        }
       else
       {
            startActivity(new Intent(MainActivity.this,First_Page.class));
            finish();
       }
            android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1D024D")));



        /////////////////////////////////////////////////////////////////////////// google part
         signInButton = findViewById(R.id.sign_in_button);
        //signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(this);
        //signInButton.setBackground(new ColorDrawable(Color.parseColor("#c73e2d")));;
        signInButton.setTextColor(Color.BLACK);
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient =new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions).build();
        ///////////////////////////////////////////////
        //facebook part
        callbackManager = CallbackManager.Factory.create();
        loginButton= (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email", "user_photos", "public_profile"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                //  Toast.makeText(getApplicationContext(),""+loginResult.getAccessToken(),Toast.LENGTH_SHORT).show();
                final String accessToken = loginResult.getAccessToken().getToken();
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("LoginActivity", response.toString());
                        // Get facebook data from login
                        try {
                           String FaceEmail= (String)object.get("id");
                            //object.get("gender");
                            if(FaceEmail!=null)
                            {
                                if(isNetworkAvailable()) {
                                    checkEmail(FaceEmail, "f");
                                }

                            }
                            else
                            {
                                Toast.makeText(MainActivity.this,"Please try another method to login ",Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                     //   Bundle bFacebookData = getFacebookData(object);

                    }

                });

                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),"Cancled ",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getApplicationContext(),""+exception,Toast.LENGTH_SHORT).show();
            }
        });
        //////////////////////////////////////////////
        username = (EditText)findViewById(R.id.cust);
        password1 =(EditText)findViewById(R.id.pass);
       // tvIsConnected =(TextView)findViewById(R.id.tv);
        loginButton1 =(Button)findViewById(R.id.email_sign_in_button2);
        x = new ProgressDialog(this);

        //printHashKey(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);

        }
    }
/*
    public  void printHashKey(Context pContext) {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("keyHash", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("keyHash", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("keyHash", "printHashKey()", e);
        }
    }
  */

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public void log(View view) {
        if(isNetworkAvailable()) {
            show();
            sendRequest();
        }
    }
    public void regBu(View view) {
        show();
        startActivity(new Intent(getBaseContext(),SignUp.class));
        x.cancel();

    }

    private void show() {

        x.setMessage("Loging...");
        x.setCancelable(false);
        x.show();
    }

    public void sendRequest() {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(MainActivity.this);
                   result = http.SEND_POST("http://188.165.159.59/testapp/dal/Login.asmx/Login", username.getText().toString(), password1.getText().toString());
                  //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                      //      +username.getText().toString()+"&password="+password1.getText().toString());
                    if (result.contains("id")) {
                        String ID = new JSONObject(result).getString("id");
                        http.setID(ID);
                        Log.d("String", result);
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
                    jsonObject.put("email  ", username.getText().toString());
                    jsonObject.put("password", password1.getText().toString());

                    params = jsonObject.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void aVoid) {

               if(result.contains("login succ")) {

                   x.cancel();
                  // Toast.makeText(getBaseContext(), "Welcome", Toast.LENGTH_SHORT).show();
                  // JSONObject jb = null;
                   try {
                       String ID = new JSONObject(result).getString("id");
                       //String accessToken1 = new JSONObject(result).getString("email");
                       AttolSharedPreference mySharedPreference = new AttolSharedPreference(MainActivity.this);
                       mySharedPreference.setKey("id", ID);
                       x.cancel();
                           startActivity(new Intent(getBaseContext(),First_Page.class));
                           finish();

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
    public void sendRequest2(final String email) {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(MainActivity.this);
                    result = http.SEND_POST1("http://188.165.159.59/testapp/dal/Login.asmx/Login", email , "123");
                    //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                    //      +username.getText().toString()+"&password="+password1.getText().toString());
                    if (result.contains("id")) {
                        String ID = new JSONObject(result).getString("id");
                        http.setID(ID);
                        Log.d("String", result);
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
                    jsonObject.put("email  ", email);
                    jsonObject.put("password","123" );


                    params = jsonObject.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                if(result.contains("login succ")) {

                    x.cancel();
                  //  Toast.makeText(getBaseContext(), "Welcome", Toast.LENGTH_SHORT).show();
                    // JSONObject jb = null;
                    try {
                        String ID = new JSONObject(result).getString("id");
                        //String accessToken1 = new JSONObject(result).getString("email");
                        AttolSharedPreference mySharedPreference = new AttolSharedPreference(MainActivity.this);
                        mySharedPreference.setKey("id", ID);
                        x.cancel();
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


    public void ForgetPassClick(View view) {
        startActivity(new Intent(MainActivity.this,ForgetPass.class));
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Google Part
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                   signIn();
                   break;
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    public void signIn ()
    {
   Intent intent =Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
   startActivityForResult(intent,REQ_CODE);
    }
    public void handleResult (GoogleSignInResult result)
    {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            String email = account.getEmail();
            if(email!=null) {
                upateUI(true,email);
            }
            else
            {
                upateUI(false,"");
            }
        }
        else
            upateUI(false,"");

    }
    public void upateUI (Boolean isLogin,String email)
    {
        if (isLogin )
        {

                checkEmail(email,"g");

        }
        else
        {
            Toast.makeText(MainActivity.this,"Please try another method to login ",Toast.LENGTH_SHORT).show();
        }

    }

    public void checkEmail(final String email, final String x) {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    HttpRequestSender http = new HttpRequestSender(MainActivity.this);
                    result_check = http.SEND_POST_check("http://188.165.159.59/testapp/dal/Login.asmx/check_facebook",email);
                    //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                    //      +username.getText().toString()+"&password="+password1.getText().toString());

                    Log.d("String", result_check);


                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("email",email);


                    params_check = jsonObject.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void aVoid) {//i stopped here

                if(result_check.contains("succ")) {

                   sendRequest2(email);


                }
                else
                {
                    if(x=="f") {
                        Intent intent2 = new Intent(MainActivity.this, facebook_google_sign_up.class);
                        intent2.putExtra("Femail", email);
                        startActivity(intent2);
                    }
                    else if (x=="g")
                    {
                        Intent intent2 = new Intent(MainActivity.this,facebook_google_sign_up.class);
                        intent2.putExtra("email",email);
                        startActivity(intent2);
                    }



                }
                super.onPostExecute(aVoid);

            }
        }.execute();

    }




    ///////////////////////////////////////////////////////////////////////////////////////////////////////
}
