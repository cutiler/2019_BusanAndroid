package net.koreate.test_20190716_adapterview;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

import net.koreate.test_20190716_adapterview.util.SQLiteHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CursorAdapterActivity extends AppCompatActivity {

    @BindView(R.id.cursor_adapter_listView)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursor_adapter);
        ButterKnife.bind(this);
        SQLiteHelper helper = new SQLiteHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        helper.onUpgrade(db, 1,2);

        SQLiteDatabase db1 = helper.getReadableDatabase();
        Cursor cursor = db1.rawQuery("SELECT * FROM tbl_dog",null);

        CursorAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                cursor,
                new String[]{"kind","name"},
                new int[]{android.R.id.text1,android.R.id.text2},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        );
        listView.setAdapter(adapter);


    }
}
