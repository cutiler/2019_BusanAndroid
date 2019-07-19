package net.koreate.test_20190719_network_valley.util;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MainController extends MultiDexApplication {

    private static MainController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    private RequestQueue mRequestQueue;

    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request,String tag){
        request.setTag(tag);
        getRequestQueue().add(request);
    }

    public static synchronized MainController getInstance(){
        return mInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        MultiDex.install(this);
        super.attachBaseContext(base);
    }
}
