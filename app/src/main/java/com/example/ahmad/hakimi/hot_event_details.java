package com.example.ahmad.hakimi;

import android.app.Activity;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmad.hakimi.basicClass.HttpRequestSender;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class hot_event_details extends AppCompatActivity {
    String phone,telephone,address,url1,result_pic2 ;
    String [] url;
    int id;
    String pay;
    int size;
    ImageView im1,im2,im3,im4,im5,im6,im7;
    double lat,lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_event_details);
        Intent intent2 = getIntent();

        telephone=intent2.getStringExtra("hotevent_text"); //to display hot offer on listview
        url1=intent2.getStringExtra("url"); //to display hot offer on listview
        id=intent2.getIntExtra("id",0); //to display hot offer on listview
        lat=intent2.getDoubleExtra("lat",0); //to display hot offer on listview
        lon=intent2.getDoubleExtra("lon",0); //to display hot offer on listview
        ///////////////////////////////////////////////////////////////////////////////////////
        ImageView imageView =(ImageView)findViewById(R.id.offerImage23);

        TextView Taddress =  (TextView)findViewById(R.id.aid);
        ///////////////////////////////////////////////////////////////////////////////////
        im1=(ImageView)findViewById(R.id.op1);
        im2=(ImageView)findViewById(R.id.op2);
        im3=(ImageView)findViewById(R.id.op3);
        im4=(ImageView)findViewById(R.id.op4);
        im5=(ImageView)findViewById(R.id.op5);
        im6=(ImageView)findViewById(R.id.op6);
        im7=(ImageView)findViewById(R.id.op7);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1D024D")));
        /////////////////////////////////////////////////////////////////////////////////////
        if(url1!=null) {
            Picasso.with(hot_event_details.this).load(url1).into(imageView);
        }

        if(telephone!=null) {
            Taddress.setText(telephone);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hot_event_details.this,pic_show.class);
                intent.putExtra("pic",url1);
                startActivity(intent);

            }
        });

        Button agentName = (Button) findViewById(R.id.lochot);
        agentName.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                //map
               Intent intent3 =new Intent(hot_event_details.this,Map.class);

                intent3.putExtra("hotlat",lat);
                intent3.putExtra("hotlon",lon);
                startActivity(intent3);
            }
        });
        ImageView share=(ImageView)findViewById(R.id.ShareNEwsBUID);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String body=telephone;
                String sub = "Hot Event";
                intent.putExtra(Intent.EXTRA_SUBJECT,sub);
                intent.putExtra(Intent.EXTRA_TEXT,body);
                startActivity(Intent.createChooser(intent,"Share using"));
            }
        });
        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hot_event_details.this,pic_show.class);
                if(0<size && url[0]!=null) {
                    intent.putExtra("pic",url[0]);

                    startActivity(intent);
                }

            }
        });
        im2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hot_event_details.this,pic_show.class);
                if(1<size &&url[1]!=null) {
                    intent.putExtra("pic",url[1]);

                    startActivity(intent);
                }

            }
        });
        im3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hot_event_details.this,pic_show.class);
                if(2<size &&url[2]!=null) {
                    intent.putExtra("pic",url[2]);

                    startActivity(intent);
                }

            }
        });
        im4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hot_event_details.this,pic_show.class);
                if(3<size &&url[3]!=null) {
                    intent.putExtra("pic",url[3]);

                    startActivity(intent);
                }
            }
        });
        im5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hot_event_details.this,pic_show.class);
                if(4<size &&url[4]!=null) {
                    intent.putExtra("pic",url[4]);

                    startActivity(intent);
                }

            }
        });
        im6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hot_event_details.this,pic_show.class);
                if(5<size &&url[5]!=null) {
                    intent.putExtra("pic",url[5]);

                    startActivity(intent);
                }

            }
        });
        im7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hot_event_details.this,pic_show.class);
                if(6<size &&url[6]!=null) {
                    intent.putExtra("pic",url[6]);
                    startActivity(intent);
                }

            }
        });

if(isNetworkAvailable()) {
    size = getEventPic(id);
}
else
{
    size=0;
}

    }
    public int  getEventPic(final int offer_id) {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender(hot_event_details.this);
                    result_pic2 = http.SEND_POST_Hot_event_pic("http://188.165.159.59/testapp/dal/Login.asmx/getdata_hoteventpicture",offer_id);


                    Log.d("String", result_pic2);


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

                if(result_pic2.contains("url")) {
                    try {
                        JSONArray JA = new JSONArray(result_pic2);
                        size=JA.length();
                        url= new String[JA.length()];
                        ArrayList<String> urlList=new ArrayList<String>();

                        for (int i = 0; i < JA.length(); i++) {

                            JSONObject JO = (JSONObject) JA.get(i);

                            url[i] ="http://188.165.159.59/testapp/"+(String) JO.get("url");
                            // urlList.add(url[i]);

                        }
                        if (0<JA.length() && url[0].contains("http")) {
                            Picasso.with(hot_event_details.this).load(url[0]).into(im1);
                        }
                        if (1<JA.length() && url[1].contains("http")) {
                            Picasso.with(hot_event_details.this).load(url[1]).into(im2);
                        }
                        if (2<JA.length() &&url[2].contains("http")) {
                            Picasso.with(hot_event_details.this).load(url[2]).into(im3);
                        }
                        if (3<JA.length() &&url[3].contains("http")) {
                            Picasso.with(hot_event_details.this).load(url[3]).into(im4);
                        }
                        if (4<JA.length() &&url[4].contains("http")) {
                            Picasso.with(hot_event_details.this).load(url[4]).into(im5);
                        }
                        if (5<JA.length() &&url[5].contains("http")) {
                            Picasso.with(hot_event_details.this).load(url[5]).into(im6);
                        }
                        if (6 <JA.length() &&url[6].contains("http")) {
                            Picasso.with(hot_event_details.this).load(url[6]).into(im7);
                        }




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                else
                {
                    //Toast.makeText(hot_event_details.this,"Not Pic",Toast.LENGTH_SHORT).show();

                }
                super.onPostExecute(aVoid);

            }
        }.execute();


        return size;
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
