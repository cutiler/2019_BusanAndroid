package net.koreate.test_20190708_layout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FrameActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn1,btn2,btn3;
    TextView content1,content2,content3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_layout_2);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);

        content1 = findViewById(R.id.content1);
        content2 = findViewById(R.id.content2);
        content3 = findViewById(R.id.content3);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        content1.setVisibility(View.GONE);
        content2.setVisibility(View.GONE);
        content3.setVisibility(View.GONE);

        if(view == btn1){
            Toast.makeText(getApplicationContext(),"1번 컨텐츠",Toast.LENGTH_SHORT).show();
            content1.setVisibility(View.VISIBLE);
        }else if(view == btn2){
            Toast.makeText(getApplicationContext(),"2번 컨텐츠",Toast.LENGTH_SHORT).show();
            content2.setVisibility(View.VISIBLE);
        }else{
            Toast.makeText(getApplicationContext(),"3번 컨텐츠",Toast.LENGTH_SHORT).show();
            content3.setVisibility(View.VISIBLE);
        }


    }
}
