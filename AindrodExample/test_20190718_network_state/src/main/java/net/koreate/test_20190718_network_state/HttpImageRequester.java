package net.koreate.test_20190718_network_state;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HttpImageRequester {

    HttpImageTask http;

    public void request(String url, HashMap<String,String> param,HttpImageCallBack imageCallBack){
        http = new HttpImageTask(url,param,imageCallBack);
        http.execute();
    }


    private class HttpImageTask extends AsyncTask<Void,Void, Bitmap>{
        String url;
        HashMap<String,String> param;
        HttpImageCallBack imageCallBack;

        public HttpImageTask(String url, HashMap<String,String> param,HttpImageCallBack imageCallBack){
            this.url=url;
            this.param=param;
            this.imageCallBack=imageCallBack;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            this.imageCallBack.onResult(bitmap);
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {

            Bitmap bitmap = null;
            String postData="";
            PrintWriter writer = null;

            try {
                URL urlInfo = new URL(url);
                HttpURLConnection http = (HttpURLConnection)urlInfo.openConnection();
                http.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
                http.setRequestMethod("POST");
                http.setConnectTimeout(10*1000);
                http.setReadTimeout(10*1000);
                http.setDoInput(true);
                http.setDoOutput(true);

                if(param != null && param.size()>0){
                    Iterator<Map.Entry<String,String>> entries = param.entrySet().iterator();
                    int index = 0;

                    while(entries.hasNext()){
                        if(index != 0){
                            postData = postData+"&";
                        }

                        Map.Entry<String,String> mapEntry = entries.next();
                        postData = postData+mapEntry.getKey()+"="+mapEntry.getValue();
                        index++;
                    }

                    writer = new PrintWriter(new OutputStreamWriter(http.getOutputStream(),"UTF-8"));
                    writer.write(postData);
                    writer.flush();
                }

                bitmap = BitmapFactory.decodeStream(http.getInputStream());

            } catch (Exception e) {
                e.printStackTrace();
                bitmap = null;
            }finally{
                if(writer != null)writer.close();
            }

            return bitmap;
        }
    }
}
