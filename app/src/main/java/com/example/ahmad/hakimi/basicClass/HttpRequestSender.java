package com.example.ahmad.hakimi.basicClass;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.ahmad.hakimi.basicClass.AttolSharedPreference;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequestSender {
    private Context context;
    private FcmMessage context1;
    private String id ;

    public String getID() {
        return this.id;
    }
    public void setID(String id) {
        this.id=id;
    }

    public HttpRequestSender(Activity context) {
        this.context = context;
        AttolSharedPreference mySharedPreference = new AttolSharedPreference(context);
        this.id = mySharedPreference.getKey("id");
    }
    public HttpRequestSender(FcmMessage context1) {
        this.context1 = context1;
        AttolSharedPreference mySharedPreference = new AttolSharedPreference(context1);
        this.id = mySharedPreference.getKey1("id");
    }
    public String SEND_POST(String url, String userName, String password) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");


        String json = "email=" + userName + "&password=" + password + "&facebook_go_tw=";
        JSONObject jo =new JSONObject();
        jo.put("email",userName);
        jo.put("password",password);
        jo.put("facebook_go_tw","");
       // con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_POST1(String url, String userName, String password) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");


       // String json = "email=" + userName + "&password=" + password + "&facebook_go_tw=";
        JSONObject jo =new JSONObject();
        jo.put("email","");
        jo.put("password",password);
        jo.put("facebook_go_tw",userName);
        // con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_REG(String url, String remail, String rpass,String rphone,String rnatio,String raddr,String rbirth,
                           String rmirrage,String rgend,String rage) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");


       // String json = "email=" + userName + "&password=" + password ;
        JSONObject jsonObject1 =new JSONObject();
        jsonObject1.put("email", remail);
        jsonObject1.put("facebook_go_tw", "");
        jsonObject1.put("password", rpass);
        jsonObject1.put("phone", rphone);
        jsonObject1.put("nationality", "");
        jsonObject1.put("address", raddr);
        jsonObject1.put("brith_day", rbirth);
        jsonObject1.put("marriage_date", "");
        jsonObject1.put("gender", rgend);
        jsonObject1.put("age", 1);
        jsonObject1.put("network_type", "orange");
        jsonObject1.put("device_type", "android");
        jsonObject1.put("latitude", "30");
        jsonObject1.put("longitude", "30");
        // con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jsonObject1));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_REG_face(String url, String face, String google ,String rphone,String gender,String age ,String rbirth,
                           String rmirrage,String rgend,String rage) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");


        // String json = "email=" + userName + "&password=" + password ;
        JSONObject jsonObject =new JSONObject();

        if(face!=null) {
            jsonObject.put("email", "");
            jsonObject.put("facebook_go_tw", face);
            jsonObject.put("password", "123");
            jsonObject.put("phone",rphone);
            jsonObject.put("nationality", "sss");
            jsonObject.put("address", "sss");
            jsonObject.put("brith_day", "01-01-2000");
            jsonObject.put("marriage_date", "01-01-2000");
            jsonObject.put("gender", gender);
            jsonObject.put("age", Integer.parseInt(age));
            jsonObject.put("network_type", "orange");
            jsonObject.put("device_type", "android");
            jsonObject.put("latitude", "30");
            jsonObject.put("longitude", "30");
        }
        if ( google !=null)
        {
            jsonObject.put("email", "");
            jsonObject.put("facebook_go_tw", google);
            jsonObject.put("password", "123");
            jsonObject.put("phone", rphone);
            jsonObject.put("nationality", "google");
            jsonObject.put("address", "google");
            jsonObject.put("brith_day", "01-01-1995");
            jsonObject.put("marriage_date", "01-01-1994");
            jsonObject.put("gender", gender);
            jsonObject.put("age", Integer.parseInt(age));
            jsonObject.put("network_type", "orange");
            jsonObject.put("device_type", "android");
            jsonObject.put("latitude", "30");
            jsonObject.put("longitude", "30");


        }
        // con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jsonObject));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_Active(String url, String emailAc, String codeAc) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");


      //  String json = "email=" + userName + "&password=" + password ;
        JSONObject jo =new JSONObject();
        jo.put("email",emailAc);
        jo.put("code",codeAc);
        // con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_Active2(String url, int id,String childName, String childBirth) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");


        //  String json = "email=" + userName + "&password=" + password ;
        JSONObject jo =new JSONObject();
        jo.put("name_baby",childName);
        jo.put("brith_day", childBirth);
        jo.put("customerch_id", id);
        // con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_Forget(String url, String FEmail) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");


        //  String json = "email=" + userName + "&password=" + password ;
        JSONObject jo =new JSONObject();
        jo.put("email",FEmail);

        // con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_POST_Hot_offer(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");


        //  String json = "email=" + userName + "&password=" + password ;
        JSONObject jo =new JSONObject();
        // con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_POST_Sub_category(String url,int CategoryId) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");


        //  String json = "email=" + userName + "&password=" + password ;
        JSONObject jo =new JSONObject();
        jo.put("category_id",CategoryId);
        // con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_POST_Agent_location(String url,int CategoryId) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");


        //  String json = "email=" + userName + "&password=" + password ;
        JSONObject jo =new JSONObject();
        jo.put("category_id",CategoryId);
        // con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_POST_Agent(String url,int CategoryId ,int subCategoryId) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        JSONObject jo =new JSONObject();
        jo.put("category_id",CategoryId);
        jo.put("subcategory_id",subCategoryId);

        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_POST_Agent_branch(String url,int agent_id) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        JSONObject jo =new JSONObject();
        jo.put("agent_id",agent_id);


        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
}
    public String SEND_POST_Hot_Event_details(String url,int Event_id) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        JSONObject jo =new JSONObject();
        jo.put("hotevent_id",Event_id);


        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_POST_get_news(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        JSONObject jo =new JSONObject();
        jo.put("customer_id",getID());


        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_POST_get_news_agent(String url,int agent_id) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        JSONObject jo =new JSONObject();
        jo.put("agent_id",agent_id);


        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_POST_forget_password(String url,String Email , String Old , String New) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        JSONObject jsonObject =new JSONObject();
        jsonObject.put("email", Email);
        jsonObject.put("old_password", Old);
        jsonObject.put("new_password", New);


        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jsonObject));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_POST_Complaint(String url, String email, String complaint) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        JSONObject jo =new JSONObject();
        jo.put("customercomp_id",getID());
        jo.put("email",email);
        jo.put("complaint",complaint);

// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_POST_order(String url, String name, String phone,String address,int product_id) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        JSONObject jo =new JSONObject();
        jo.put("customer_id",getID());
        jo.put("product_id",product_id);
        jo.put("name",name);
        jo.put("phone",phone);
        jo.put("address",address);

// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_POST_Sugg(String url, String agentName, String agentEmail, String agentPhone, String yourEmail, String yourPhone) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        JSONObject jo =new JSONObject();
        jo.put("customersugg_id",getID());
        jo.put("nameagent",agentName);
        jo.put("phoneage",agentPhone);
        jo.put("emailage",agentEmail);
        jo.put("customer_phone",yourPhone);
        jo.put("customer_email",yourEmail);


// wait hakimo to remove complaint_it
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_POST_Follow(String url, int agentId) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        JSONObject jo =new JSONObject();
        jo.put("customer_id",getID());
        jo.put("agent_id",agentId);

        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_POST_remove_News_favorit(String url, int news_id) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        JSONObject jo =new JSONObject();
        jo.put("fav_id",news_id);


        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_POST_Add_News_favorit(String url, int news_id) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        JSONObject jo =new JSONObject();
        jo.put("customer_id",getID());
        jo.put("news_id",news_id);
        jo.put("offer_id","0");

        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }

    public String SEND_POST_Add_offer_favorit(String url, int news_id) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        JSONObject jo =new JSONObject();
        jo.put("customer_id",getID());
        jo.put("news_id",0);
        jo.put("offer_id",news_id);

        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_POST_Add_offer_pic(String url, int news_id) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        JSONObject jo =new JSONObject();
        jo.put("offer_id",news_id);

        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_POST_Hot_event_pic(String url, int news_id) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        JSONObject jo =new JSONObject();
        jo.put("hotevent_id",news_id);

        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_POST_get_news_fav(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        JSONObject jo =new JSONObject();
        jo.put("customer_id",getID());


        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_POST_edit_noti(String url,String status) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        JSONObject jo =new JSONObject();
        jo.put("customer_id",getID());
        jo.put("state",status);


        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_POST_improve(String url,String status) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        JSONObject jo =new JSONObject();
        jo.put("customer_id",getID());
        jo.put("text",status);


        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_POST_get_customer_info(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        JSONObject jo =new JSONObject();
        jo.put("customer_id",getID());


        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_POST_Agent_unfollow(String url,int agent_id) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        JSONObject jo =new JSONObject();
        jo.put("agent_id",agent_id);
        jo.put("customer_id",getID());

        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_POST_Agent_offers(String url,int agent_id) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        JSONObject jo =new JSONObject();
        jo.put("agent_id",agent_id);


        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_POST_check(String url,String email) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        JSONObject jo =new JSONObject();
        jo.put("email",email);


        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

         while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
    public String SEND_POST_Hot_event_pic(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        JSONObject jo =new JSONObject();
        jo.put("hotevent_id",1);


        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(String.valueOf(jo));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        Log.e("responseCode", String.valueOf(responseCode));
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        response.toString();

        return response.toString();
    }
}
