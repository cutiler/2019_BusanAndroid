package net.koreate.test_20190718_network_state;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HttpRequester {

    private HttpTask http;

    public HttpRequester(){}

    public void request(String url,HashMap<String,String> param,HttpCallBack callBack){
        http = new HttpTask(url,param,callBack);
        http.execute();
    }



    private class HttpTask extends AsyncTask<Void,Void,String>{
        String url;
        HashMap<String,String> param;
        HttpCallBack callBack;

        @Override
        protected void onPostExecute(String s) {
            System.out.println("Http request : "+s);
            this.callBack.onResult(s);
        }

        public HttpTask(String url, HashMap<String,String> param, HttpCallBack callBack){
            this.url = url;
            this.param = param;
            this.callBack = callBack;
        }

        @Override
        protected String doInBackground(Void... voids) {

            String response ="";
            String postData ="";
            PrintWriter writer = null;
            BufferedReader reader =null;


            try {
                URL text = new URL(url);
                HttpURLConnection http = (HttpURLConnection) text.openConnection();
                http.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
                http.setConnectTimeout(10*1000);
                http.setReadTimeout(10*1000);
                http.setDoInput(true);
                http.setDoOutput(true);
                http.setRequestMethod("GET");

                if(param != null && param.size() > 0){
                    Iterator<Map.Entry<String,String>> entries = param.entrySet().iterator();
                    int index = 0;
                    while(entries.hasNext()){
                        if(index != 0){
                            postData = postData+"&";
                        }
                        Map.Entry<String,String> mapEntry = entries.next();
                        postData = postData+ mapEntry.getKey()+"="+mapEntry.getValue();
                        index++;
                    }

                    writer = new PrintWriter(new OutputStreamWriter(http.getOutputStream(),"UTF-8"));
                    writer.write(postData);
                    writer.flush();
                }

                reader = new BufferedReader(new InputStreamReader(http.getInputStream(),"UTF-8"));
                StringBuffer sb = new StringBuffer();
                String readLine;
                while((readLine= reader.readLine())!=null){
                    sb.append(readLine);
                }
                response = sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                try {
                    if(writer != null)writer.close();
                    if(reader != null)reader.close();
                } catch (IOException e) {}
            }
            return response;
        }
    }

}
