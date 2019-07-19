package net.koreate.test_20190718_network_state;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import java.util.ArrayList;

public class NetworkCheck {

    Context context;

    public NetworkCheck(Context context){
        this.context = context;
    }

    public boolean checkNetworkInfo(ArrayList<String> list){
        boolean isConnected = false;

        ConnectivityManager manager
                = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = manager.getActiveNetworkInfo();
        if(info != null){
            if(info.getType() == ConnectivityManager.TYPE_WIFI){
                list.add("Network Info : onLine -"+info.getTypeName());
            }else if(info.getType() == ConnectivityManager.TYPE_MOBILE){
                list.add("Network Info : onLine -"+info.getTypeName());
            }
            isConnected = true;
        }else{
            list.add("Network Info : offLine");
        }
       return isConnected;
    }

    public void checkWifi(ArrayList<String> list){
        WifiManager manager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        if(!manager.isWifiEnabled()){
            list.add("wifi :  disabled");
        }else{
            list.add("Wifi : enabled");
        }
        // wifi 수신감도
        // 수신률 -> 0 ~ -150 dbm
        int rssi = manager.getConnectionInfo().getRssi();
        // -30 ~ -50 원할
        // -51 ~ -84 느리지만 통신 가능한 상태
        // -85 ~ -95 연결이 끊겼다가 돌아왔다가
        // -95 ~ 수신불량
        list.add("rssi 정보 : " + rssi);


    }

}
