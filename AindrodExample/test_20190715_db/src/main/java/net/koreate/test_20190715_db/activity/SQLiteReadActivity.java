package net.koreate.test_20190715_db.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import net.koreate.test_20190715_db.R;
import net.koreate.test_20190715_db.util.SQLiteMemoDBHelper;
import net.koreate.test_20190715_db.vo.MemoVO;

public class SQLiteReadActivity extends AppCompatActivity {

    TextView add_title, add_content;

    Button list_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_read);

        add_title = findViewById(R.id.add_title);
        add_content = findViewById(R.id.add_content);

        MemoVO memo = new SQLiteMemoDBHelper(this).readLastMemo();
        add_title.setText(memo.getTitle());
        add_content.setText(memo.getContent());

        list_btn = findViewById(R.id.list_btn);
        list_btn.setOnClickListener(view -> {
            Intent i = new Intent(SQLiteReadActivity.this,MemoListActivity.class);
            startActivity(i);
        });

    }
}
