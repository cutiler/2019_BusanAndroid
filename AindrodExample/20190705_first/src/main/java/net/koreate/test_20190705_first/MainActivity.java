package net.koreate.test_20190705_first;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    EditText editText;
    CheckBox checkBox;
    TextView result;

    Button sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        checkBox = findViewById(R.id.checkBox);
        result = findViewById(R.id.result);
        sendBtn = findViewById(R.id.sendBtn);

        /* listener 직접 구현 시 정의  */
/*
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                runCheckedChange(isChecked);
            }
        });*/

        /* 구현객체를 통해 정의 */
        //checkBox.setOnCheckedChangeListener(this);

        /* Listener 객체  직접 생성  */
        checkBox.setOnCheckedChangeListener(listener);

        /* EditText text 변경 감지*/
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable editable) {
                System.out.println("작성내용 : " + editable);
                //result.setText(editable.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }


        });
        /* EditText 변경 감지 이벤트 끝  */

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String resultText = editText.getText().toString();
                result.setText(resultText);
                editText.setText("");
            }
        });

    }

    CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            runCheckedChange(b);
        }
    };


    public  void runCheckedChange(boolean isChecked){
        String text = result.getText().toString();
        System.out.println(text);
        if(isChecked){
            System.out.println("체크");
            String funny = getString(R.string.funny);
            text = funny;
        }else{
            System.out.println("체크해제");
            String boring = getString(R.string.boring);
            text = boring;
        }
        result.setText(text);
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        runCheckedChange(b);
    }
}
