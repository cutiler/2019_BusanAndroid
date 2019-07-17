package net.koreate.test_20190715_db.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import net.koreate.test_20190715_db.R;
import net.koreate.test_20190715_db.util.SQLiteMemoDBHelper;
import net.koreate.test_20190715_db.vo.MemoVO;

public class SQLiteActivity extends AppCompatActivity {

    Button add_btn;
    EditText add_title;
    EditText add_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        add_btn = findViewById(R.id.add_btn);
        add_title = findViewById(R.id.add_title);
        add_content = findViewById(R.id.add_content);

        /*
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {}
        });
        */

        add_btn.setOnClickListener(v ->{
            String title = add_title.getText().toString();
            String content = add_content.getText().toString();
            Log.i("SQLiteActivity " , "title : "+title+" // content : "+content);
            SQLiteMemoDBHelper helper = new SQLiteMemoDBHelper(this);
            helper.insertMemo(title,content);

            add_title.setText("");
            add_content.setText("");

            MemoVO memo = helper.readLastMemo();
            Log.i("READ " , memo.toString());

            Intent i = new Intent(SQLiteActivity.this,SQLiteReadActivity.class);
            startActivity(i);
        });
    }
}
