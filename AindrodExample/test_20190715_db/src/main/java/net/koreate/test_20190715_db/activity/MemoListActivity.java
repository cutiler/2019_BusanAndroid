package net.koreate.test_20190715_db.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import net.koreate.test_20190715_db.R;
import net.koreate.test_20190715_db.util.MemoListViewAdapter;
import net.koreate.test_20190715_db.util.SQLiteMemoDBHelper;
import net.koreate.test_20190715_db.vo.MemoVO;

import java.util.ArrayList;

public class MemoListActivity extends AppCompatActivity {

    // 수정 창
    LinearLayout modifyWindow;
    TextView modifyID;
    EditText modifyTitle;
    EditText modifyContent;
    Button modifyBtn;

    // 메모 목록창
    ListView listView;
    Button addBtn;

    ArrayList<MemoVO> memoList;

    MemoVO memo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_list);

        listView = findViewById(R.id.listView);
        modifyWindow = findViewById(R.id.modifyWindow);
        modifyID = findViewById(R.id.modifyID);
        modifyTitle = findViewById(R.id.modifyTitle);
        modifyContent = findViewById(R.id.modifyContent);
        modifyBtn = findViewById(R.id.modifyBtn);


        SQLiteMemoDBHelper helper = new SQLiteMemoDBHelper(this);
        ArrayList<String> titleList = helper.readTitleList();
        for(String title : titleList){
            System.out.println("read titles : "+title);
        }


        // 리스트뷰 데이터 adapter
        //ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,titleList);
        memoList = helper.readMemoList();
        MemoListViewAdapter adapter
                = new MemoListViewAdapter(this,memoList,R.layout.custom_listview_item);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                memo = memoList.get(position);
                Log.i("MemoVO",memo.toString());
                modifyID.setText(String.valueOf(memo.getId()));
                modifyTitle.setText(memo.getTitle());
                modifyContent.setText(memo.getContent());
                modifyWindow.setVisibility(View.VISIBLE);
            }
        });

        // 수정 처리
        modifyBtn.setOnClickListener(view ->{
            String title = modifyTitle.getText().toString();
            String content = modifyContent.getText().toString();
            memo.setTitle(title);
            memo.setContent(content);
            //memo.setId(Integer.parseInt(modifyID.getText().toString()));
            helper.updateMemo(memo);
            modifyWindow.setVisibility(View.GONE);

            adapter.notifyDataSetChanged();

        });


        // 메모 추가 화면으로 이동
        addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(view->{
            startActivity(new Intent(MemoListActivity.this,SQLiteActivity.class));
            finish();
        });





    }
}
