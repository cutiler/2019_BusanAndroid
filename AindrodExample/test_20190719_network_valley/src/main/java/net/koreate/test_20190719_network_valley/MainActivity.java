package net.koreate.test_20190719_network_valley;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btn1)
    Button listBtn;

    @BindView(R.id.btn2)
    Button mapBtn;

    @BindView(R.id.btn3)
    Button uploadBtn;

    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        listBtn.setOnClickListener(this);
        mapBtn.setOnClickListener(this);
        uploadBtn.setOnClickListener(this);
    }

    private void checkNetwork(View view) {
        ConnectivityManager manager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if(info != null){
            // 통신 가능 상태
            Toast.makeText(this, "네트워크 연결"+info.getTypeName(), Toast.LENGTH_SHORT).show();
            if(view == listBtn){
                startActivity(new Intent(this,VolleyListActivity.class));
            }else if(view == mapBtn){
                startActivity(new Intent(this,VolleyMapActivity.class));
            }else{
                startActivity(new Intent(this,UploadActivity.class));
            }
        }else{
            // 통신 불가능 상태
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("서비스를 이용할 수 없습니다.");
            builder.setMessage("네트워크가 불안정합니다 \n 네트워크 상태를 확인후 다시 이용해주세요");
            builder.setPositiveButton("확인",(dialog,id)->{
                Intent i = new Intent(Settings.ACTION_WIFI_SETTINGS);
                startActivityForResult(i,1);
            });
            builder.setCancelable(false);
            builder.create().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            checkNetwork(view);
        }
    }

    @Override
    public void onClick(View view) {
        this.view = view;
        checkNetwork(view);
    }
}
