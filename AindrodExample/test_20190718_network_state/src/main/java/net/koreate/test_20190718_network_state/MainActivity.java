package net.koreate.test_20190718_network_state;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btnState)
    Button btnState;

    @BindView(R.id.btnNetwork)
    Button btnNetwork;

    @BindView(R.id.btnVolley)
    Button btnVolley;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        btnState.setOnClickListener(view -> {
            Intent i = new Intent(this,NetworkStateActivity.class);
            startActivity(i);
        });

        btnNetwork.setOnClickListener(view ->{
            Intent i = new Intent(this,NetworkActivity.class);
            startActivity(i);
        });
        btnVolley.setOnClickListener(view->{
            Intent i = new Intent(this,VolleyActivity.class);
            startActivity(i);
        });
    }
}
