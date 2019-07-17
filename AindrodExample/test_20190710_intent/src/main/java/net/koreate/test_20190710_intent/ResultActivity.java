package net.koreate.test_20190710_intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.koreate.test_20190710_intent.vo.MemberVO;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultActivity extends AppCompatActivity {

    final static String USER_ID = "aaa";
    final static String USER_PW = "bbb";

    @BindView(R.id.textView)
    TextView result;

    @BindView(R.id.callback)
    Button callback;

    boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);

        MemberVO member =  (MemberVO)getIntent().getSerializableExtra("member");
        result.setText(member.toString());

        if(USER_ID.equals(member.getUserId()) && USER_PW.equals(member.getUserPw())){
            isLogin = true;
        }

        result.setText("isLogin : "+isLogin);

        callback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack();
            }
        });
    }

    public void callBack(){
        Intent i = ResultActivity.this.getIntent();
        i.putExtra("isLogin",isLogin);
        setResult(RESULT_OK,i);
        //RESULT_CANCELED
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*Log.i("backPressed","click");
        callBack();*/
    }
}
