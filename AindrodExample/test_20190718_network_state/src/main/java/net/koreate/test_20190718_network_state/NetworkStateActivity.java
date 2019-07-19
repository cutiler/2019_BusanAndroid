package net.koreate.test_20190718_network_state;

import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NetworkStateActivity extends AppCompatActivity {

    @BindView(R.id.listView)
    ListView listView;

    ArrayList<String> list;
    ArrayAdapter<String> adapter;

    NetworkCheck networkCheck;

    NetworkBroadCastReceiver receiver;

    boolean isConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_state);
        ButterKnife.bind(this);
        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                list
        );
        listView.setAdapter(adapter);

        networkCheck = new NetworkCheck(this);
        isConnected = networkCheck.checkNetworkInfo(list);

        if(!isConnected){
            AlertDialog.Builder builder
                    = new AlertDialog.Builder(NetworkStateActivity.this);
            builder.setTitle("NETWORK INFO");
            builder.setMessage("네트워크 상태가 원할 하지 않습니다.\n 이용에 불편을 드려 죄송합니다. \n 네트워크 확인 후 이용바랍니다.");
            builder.setPositiveButton("확인",(dialog, id)->{
                dialog.dismiss();
                ActivityCompat.finishAffinity(NetworkStateActivity.this);
                System.runFinalizersOnExit(true);
                System.exit(0);
            });
            builder.setCancelable(false);
            builder.create().show();
        }



        list.add("isConnected : " + isConnected);
        networkCheck.checkWifi(list);

        adapter.notifyDataSetChanged();

        receiver = new NetworkBroadCastReceiver(this);

        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        registerReceiver(receiver,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
