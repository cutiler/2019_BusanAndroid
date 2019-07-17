package net.koreate.firstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MemberVO m = new MemberVO();
        m.setA("aa");
        m.getA();
        System.out.println(m.getA());
    }
}
