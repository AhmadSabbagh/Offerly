package com.example.ahmad.hakimi.basicClass;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmad.hakimi.Agent_profile;
import com.example.ahmad.hakimi.First_Page;
import com.example.ahmad.hakimi.HomePage;
import com.example.ahmad.hakimi.ListViewActivity;
import com.example.ahmad.hakimi.NewFavoriteAvtivity;
import com.example.ahmad.hakimi.R;
import com.example.ahmad.hakimi.fullnewsactivity;
import com.example.ahmad.hakimi.pic_show;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by ahmad on 12/12/2017.
 */

public class MyCustomerAdapterNews extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();//news name
    private ArrayList<String> list1 = new ArrayList<String>();//news text
    private ArrayList<String> list2 = new ArrayList<String>();//agent name
    private ArrayList<String> list3 = new ArrayList<String>();//agent pic
    private int [] Agent_Id,news_id,fav_id;
    private Context context;
    private Boolean s,status;
    ImageButton  add;
    String result_news,result_Agent_branch,params_Agent_branch ,result_Agent_offer,params_Agent_offer;





    public MyCustomerAdapterNews(ArrayList<String> list,ArrayList<String> list1,ArrayList<String> list2,ArrayList<String> list3,int [] Agent_Id,int []  news_id,Boolean s ,Context context)
    {
        this.list = list;
        this.list1=list1;
        this.list2=list2;
        this.list3=list3;
        this.context = context;
        this.Agent_Id=Agent_Id;
        this.news_id=news_id;
        this.s=s;

    }

    public MyCustomerAdapterNews(ArrayList<String> list,ArrayList<String> list1,ArrayList<String> list2,ArrayList<String> list3,int [] Agent_Id,int []  news_id,int [] fav_id,Boolean s ,Context context)
    {
        this.list = list;
        this.list1=list1;
        this.list2=list2;
        this.list3=list3;
        this.context = context;
        this.Agent_Id=Agent_Id;
        this.fav_id=fav_id;
        this.news_id=news_id;
        this.s=s;


    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_news_view, null);
           // view.setBackgroundResource(R.drawable.listshape);
        }

       final  Button remove;
        add = (ImageButton)view.findViewById(R.id.addfavID);
        remove =(Button)view.findViewById(R.id.removeID);

        if(s)
        {
            add.setVisibility(View.VISIBLE);
            remove.setVisibility(View.INVISIBLE);

        }
        else
        {
            remove.setVisibility(View.VISIBLE);
            add.setVisibility(View.INVISIBLE);
        }


        if (s&&fav_id!=null) {
            status=false;
            for (int i = 0; i < fav_id.length; i++) {
             if(news_id[position]==fav_id[i])
             {
                 add.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_on));
                // add.setEnabled(false);
                 status=true;
             }
            }
            if(status==false)
            {

                add.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_off));
            }
        }
        TextView newsName = (TextView)view.findViewById(R.id.News_nameID);
        newsName.setText(list.get(position));
        TextView newsText = (TextView)view.findViewById(R.id.News_text);
        newsText.setText(list1.get(position));
        if (newsText.length() > 20) {
            //newsText = newsText.substring(0, 8) + "...";
        }
        Button agentName = (Button) view.findViewById(R.id.Agent_nameId);
        agentName.setText(list2.get(position));
        ImageView imageView = (ImageView) view.findViewById(R.id.newsImg);
        Picasso.with(context).load(list3.get(position)).into(imageView);



        add.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    add.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), android.R.drawable.btn_star_big_on));
                    AddFav(news_id[position]);
                    AlertDialog.Builder a_builder = new AlertDialog.Builder(context);
                    a_builder.setMessage("News Added !!!")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    context.startActivity(new Intent(context, First_Page.class));
                                    ((Activity) context).finish();
                                }
                            });
                    AlertDialog alert = a_builder.create();
                    alert.setTitle("Thanks !!!");
                    alert.show();
                    notifyDataSetChanged();
                }
            }
        });

        remove.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    removeFav(fav_id[position]);
                    AlertDialog.Builder a_builder = new AlertDialog.Builder(context);
                    a_builder.setMessage("News Removed from Favourite !!!")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    context.startActivity(new Intent(context, NewFavoriteAvtivity.class));
                                    ((Activity) context).finish();
                                }
                            });
                    AlertDialog alert = a_builder.create();
                    alert.setTitle("Done !!!");
                    alert.show();
                    notifyDataSetChanged();
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,pic_show.class);
                intent.putExtra("pic", list3.get(position));
                context.startActivity(intent);
                notifyDataSetChanged();
            }
        });
        final Button read = (Button)view.findViewById(R.id.ReadMoreID);

        read.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                //deleteBtn.setVisibility(View.INVISIBLE);
               String x= list.get(position);
                Intent intent = new Intent(context,fullnewsactivity.class);
                intent.putExtra("news_name", list.get(position));
                intent.putExtra("news_text", list1.get(position));
                intent.putExtra("agent_name", list2.get(position));
                intent.putExtra("news_pic", list3.get(position));
                intent.putExtra("state", "false");
               context.startActivity(intent);

                notifyDataSetChanged();
            }
        });

        agentName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                //deleteBtn.setVisibility(View.INVISIBLE);
                String x= list.get(position);
                Intent intent = new Intent(context,Agent_profile.class);
                intent.putExtra("agent_name", list2.get(position));
                intent.putExtra("agent_id", Agent_Id[position]);
                context.startActivity(intent);

                notifyDataSetChanged();
            }
        });
        return view;
    }




    public void AddFav(final int new_id) {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender((Activity) context);
                    result_news = http.SEND_POST_Add_News_favorit("http://188.165.159.59/testapp/dal/Login.asmx/add_fav",new_id);
                    //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                    //      +username.getText().toString()+"&password="+password1.getText().toString());

                    Log.d("String", result_news);


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

                if(result_news.contains("succ")) {

                    //   x.cancel();
                   // Toast.makeText(context, "News Added to Favourtie", Toast.LENGTH_SHORT).show();




                }
                else
                {
                    //Toast.makeText(context,"News Not Added",Toast.LENGTH_SHORT).show();

                }
                super.onPostExecute(aVoid);

            }
        }.execute();

    }
    public void removeFav(final int new_id) {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender((Activity) context);
                    result_news = http.SEND_POST_remove_News_favorit("http://188.165.159.59/testapp/dal/Login.asmx/remove_fav",new_id);
                    //  result = http.SEND_Check("http://188.165.159.59/testapp/dal/Login.asmx/Login?email="
                    //      +username.getText().toString()+"&password="+password1.getText().toString());

                    Log.d("String", result_news);


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

                if(result_news.contains("succ")) {

                    //   x.cancel();
                 //   Toast.makeText(context, "News Removed", Toast.LENGTH_SHORT).show();

                    // add.setText("added to Favourite");


                }
                else
                {
                  //  Toast.makeText(context,"News Not Removed",Toast.LENGTH_SHORT).show();

                }
                super.onPostExecute(aVoid);

            }
        }.execute();

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
