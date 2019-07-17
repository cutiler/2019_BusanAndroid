package net.koreate.test_20190710_intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import net.koreate.test_20190710_intent.vo.MemberVO;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReceiveActivity extends AppCompatActivity {

    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        ButterKnife.bind(this);

        Intent i =  this.getIntent();

        String userId = i.getStringExtra("userId");
        String userPw = i.getStringExtra("userPw");
        int uno = i.getIntExtra("uno",0);
        Log.i("uno " , uno+"");
        boolean isChecked = i.getBooleanExtra("isChecked",false);
        Log.i("isChecked " , isChecked+"");
        String [] 점심 = i.getStringArrayExtra("strs");
        if(점심 != null){
            for(String s : 점심){
                Log.i("메뉴 ",s);
            }
        }

        MemberVO member = (MemberVO)i.getSerializableExtra("memberVO");
        Log.i("Member VO ",member.toString());

        ArrayList<MemberVO> memberList = (ArrayList<MemberVO>)i.getSerializableExtra("memberList");
        for(MemberVO m : memberList){
            Log.i("memberList",m.toString());
        }

        textView.setText(userId+" / "+userPw);

    }
}
