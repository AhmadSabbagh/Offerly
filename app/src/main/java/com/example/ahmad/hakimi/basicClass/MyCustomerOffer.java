package com.example.ahmad.hakimi.basicClass;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
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
import com.example.ahmad.hakimi.ListViewActivity;
import com.example.ahmad.hakimi.NewFavoriteAvtivity;
import com.example.ahmad.hakimi.R;
import com.example.ahmad.hakimi.pic_show;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.app.PendingIntent.getActivity;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by ahmad on 12/12/2017.
 */

public class MyCustomerOffer extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();//offer name
    private ArrayList<String> list1 = new ArrayList<String>();//offer desc
    private ArrayList<String> list2 = new ArrayList<String>();//offer pic
    private int [] offerID,news_id,fav_id;
    private Context context;
    String result_news,result_pic,result_pic2;
    Button showPic;
    Boolean s;
    ImageButton AddFav;
    Boolean status;



    public MyCustomerOffer(ArrayList<String> list,ArrayList<String> list1,ArrayList<String> list2,int [] offerID,int [] fav_id,Boolean s ,Context context) {
        this.list = list;
        this.list1=list1;
        this.list2=list2;
        this.context = context;
        this.offerID=offerID;
        this.s=s;
        this.fav_id=fav_id;

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
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_fav_offer, null);
            //view.setBackgroundResource(R.drawable.listshape);
        }
        if(isNetworkAvailable()) {
            showPic1(offerID[position], view);
        }
        TextView offerTitle = (TextView)view.findViewById(R.id.offerTitleID);
        offerTitle.setText(list.get(position));

        TextView offerDesc = (TextView)view.findViewById(R.id.offerDescId);
        offerDesc.setText(list1.get(position));
        ImageView imageView = (ImageView)view.findViewById(R.id.offerImage23);
        Picasso.with(context).load(list2.get(position)).into(imageView);
///////////////////////////////////////////////////////////////////////////////////////


        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,pic_show.class);
                intent.putExtra("pic", list2.get(position));
                context.startActivity(intent);
                notifyDataSetChanged();
            }
        });



///////////////////////////////////////////////////////////////////////////////////////




         //showPic = (Button) view.findViewById(R.id.showPicBU);
        final  Button remove;
        AddFav = (ImageButton)view.findViewById(R.id.addFavButton);
        remove =(Button)view.findViewById(R.id.removeID);
        if(s)
        {
            AddFav.setVisibility(View.VISIBLE);
            remove.setVisibility(View.INVISIBLE);

        }
        else
        {
            remove.setVisibility(View.VISIBLE);
            AddFav.setVisibility(View.INVISIBLE);
        }
        if (s&&fav_id!=null) {
            status = false;
            for (int i = 0; i < fav_id.length; i++) {
                if (offerID[position] == fav_id[i]) {
                    AddFav.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), android.R.drawable.btn_star_big_on));
                    // add.setEnabled(false);
                    status = true;
                }
            }
            if (status == false) {

                AddFav.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), android.R.drawable.btn_star_big_off));
            }

        }
        AddFav.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(isNetworkAvailable()) {
                    AddFav(offerID[position]);

                    AlertDialog.Builder a_builder = new AlertDialog.Builder(context);
                    a_builder.setMessage("Offer Added !!!")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    context.startActivity(new Intent(context, NewFavoriteAvtivity.class));
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
                //do something
                //deleteBtn.setVisibility(View.INVISIBLE);
                if(isNetworkAvailable()) {
                    removeFav(fav_id[position]);
                    AlertDialog.Builder a_builder = new AlertDialog.Builder(context);
                    a_builder.setMessage("Offer Removed from Favourite !!!")
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





        return view;
    }

    public void showPic1(final int offer_id, final View view) {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender((Activity) context);
                    result_pic2 = http.SEND_POST_Add_offer_pic("http://188.165.159.59/testapp/dal/Login.asmx/getdata_offerpictur",offer_id);


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

                        String[] url= new String[JA.length()];
                        ArrayList<String> urlList=new ArrayList<String>();

                        for (int i = 0; i < JA.length(); i++) {

                            JSONObject JO = (JSONObject) JA.get(i);

                            url[i] ="http://188.165.159.59/testapp/"+(String) JO.get("url");
                           // urlList.add(url[i]);

                        }
                        ImageView im1,im2,im3,im4,im5,im6,im7;
                        im1=(ImageView)view.findViewById(R.id.op1);
                        im2=(ImageView)view.findViewById(R.id.op2);
                        im3=(ImageView)view.findViewById(R.id.op3);
                        im4=(ImageView)view.findViewById(R.id.op4);
                        im5=(ImageView)view.findViewById(R.id.op5);
                        im6=(ImageView)view.findViewById(R.id.op6);
                        im7=(ImageView)view.findViewById(R.id.op7);

                        if (0<JA.length() && url[0].contains("http")) {
                            Picasso.with(context).load(url[0]).into(im1);
                        }
                        if (1<JA.length() && url[1].contains("http")) {
                            Picasso.with(context).load(url[1]).into(im2);
                        }
                        if (2<JA.length() &&url[2].contains("http")) {
                            Picasso.with(context).load(url[2]).into(im3);
                        }
                        if (3<JA.length() &&url[3].contains("http")) {
                            Picasso.with(context).load(url[3]).into(im4);
                        }
                        if (4<JA.length() &&url[4].contains("http")) {
                            Picasso.with(context).load(url[4]).into(im5);
                        }
                        if (5<JA.length() &&url[5].contains("http")) {
                            Picasso.with(context).load(url[5]).into(im6);
                        }
                        if (6 <JA.length() &&url[6].contains("http")) {
                            Picasso.with(context).load(url[6]).into(im7);

                        }




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                else
                {
                 //   Toast.makeText(context,"Not Added",Toast.LENGTH_SHORT).show();

                }
                super.onPostExecute(aVoid);

            }
        }.execute();



    }
    public void AddFav(final int offer_id) {
        new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... voids) {
                try {

                    HttpRequestSender http = new HttpRequestSender((Activity) context);
                    result_news = http.SEND_POST_Add_offer_favorit("http://188.165.159.59/testapp/dal/Login.asmx/add_fav",offer_id);
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
                 //   Toast.makeText(context, "News Added to Favourtie", Toast.LENGTH_SHORT).show();

                   // AddFav.setText("added to Favourite");


                }
                else
                {
                   // Toast.makeText(context,"Not Added",Toast.LENGTH_SHORT).show();

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
                  //  Toast.makeText(context, "News Removed", Toast.LENGTH_SHORT).show();

                    // add.setText("added to Favourite");


                }
                else
                {
                   // Toast.makeText(context,"News Not Removed",Toast.LENGTH_SHORT).show();

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
