package net.koreate.test_20190718_network_state;

import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NetworkActivity extends AppCompatActivity {

    @BindView(R.id.text_title)
    TextView text_title;
    @BindView(R.id.text_date)
    TextView text_date;
    @BindView(R.id.text_content)
    TextView text_content;
    @BindView(R.id.image)
    ImageView image;

    NetworkBroadCastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        ButterKnife.bind(this);

        receiver = new NetworkBroadCastReceiver(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        registerReceiver(receiver,filter);

        HttpRequester requester = new HttpRequester();
        String url = "http://192.168.0.253:8080/controller/testFile";
        HashMap<String,String> param = new HashMap<>();
        requester.request(url, param, new HttpCallBack() {
            @Override
            public void onResult(String result) {
                System.out.println(result);
                try {
                    JSONObject obj = new JSONObject(result);
                    String title = obj.getString("title");
                    String date = obj.getString("date");
                    String file = obj.getString("file");
                    String content = obj.getString("content");
                    System.out.println(title+" || "+date+" || "+file+" || "+content);
                    text_title.setText(title);
                    text_date.setText(date);
                    text_content.setText(content);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        HttpImageRequester imageRequester = new HttpImageRequester();
        String imageUrl = "http://192.168.0.253:8080/controller/testImageFile";
        imageRequester.request(imageUrl,param,bitmap->{
            image.setImageBitmap(bitmap);
        });
    }
}
