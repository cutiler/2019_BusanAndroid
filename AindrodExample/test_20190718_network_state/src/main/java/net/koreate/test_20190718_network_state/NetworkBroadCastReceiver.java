package net.koreate.test_20190718_network_state;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

public class NetworkBroadCastReceiver extends BroadcastReceiver {

    Activity activity;

    public NetworkBroadCastReceiver(Activity activity){
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)){
            int state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,-1);
            if(state == WifiManager.WIFI_STATE_ENABLED){
                Toast.makeText(
                        context,
                        "WIFI_STATE_CHANGED_ACTION : enabled",
                        Toast.LENGTH_SHORT
                ).show();
            }else{
                Toast.makeText(
                        context,
                        "WIFI_STATE_CHANGED_ACTION : disabled",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }else if(intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)){
            NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            WifiManager manager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = manager.getConnectionInfo();
            String ssid = wifiInfo.getSSID();
            if(info.getState() == NetworkInfo.State.CONNECTED){
                System.out.println(" NETWORK STATE : CONNECTED -"+ssid);
            }else if(info.getState() == NetworkInfo.State.DISCONNECTED){
                System.out.println(" NETWORK STATE : DISCONNECTED -"+ssid);
                showDialog();
            }
        }else{
            WifiManager manager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            int rssi = manager.getConnectionInfo().getRssi();
            System.out.println("RSSI INFO : " + rssi);
            if(rssi < -80){
                showDialog();
            }
        }
    }

    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("네트워크 연결이 되어있지  않습니다.");
        builder.setMessage("네트워크 연결이 필요한 페이지 입니다. \n WIFI 또는 MOBILE 데이터를 확인해주세요");
        builder.setPositiveButton("확인",(dialog,id)->{
            dialog.dismiss();
            ActivityCompat.finishAffinity(activity);
            System.runFinalizersOnExit(true);
            System.exit(0);
        });
        builder.setCancelable(false);
        builder.create().show();
    }
}
