package net.koreate.test_20190716_recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindViews;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindViews({R.id.basicVerticalBtn,R.id.horizontalBtn,R.id.gridBtn,R.id.staggeredBtn})
    Button[] btns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        for(Button btn : btns){
            btn.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        Intent i = null;
        switch(view.getId()){
            case R.id.basicVerticalBtn :
                System.out.println("vertical btn click");
                i = new Intent(this,BasicVerticalActivity.class);
                break;
            case R.id.horizontalBtn :
                System.out.println("horizontalBtn click");
                i = new Intent(this,HorizontalActivity.class);
                break;
            case R.id.gridBtn :
                System.out.println("gridBtn click");
                i = new Intent(this,GridActivity.class);
                break;
            case R.id.staggeredBtn :
                System.out.println("staggeredBtn click");
                i = new Intent(this,StaggeredActivity.class);
                break;
        }
        startActivity(i);
    }
}
