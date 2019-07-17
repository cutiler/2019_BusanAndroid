package net.koreate.test_20190716_adapterview;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import net.koreate.test_20190716_adapterview.util.CustomListViewAdapter;
import net.koreate.test_20190716_adapterview.util.SQLiteHelper;
import net.koreate.test_20190716_adapterview.vo.DogVO;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomAdapterActivity extends AppCompatActivity {

    @BindView(R.id.listView)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_adapter);
        ButterKnife.bind(this);

        SQLiteHelper helper = new SQLiteHelper(this);
        ArrayList<DogVO> dogList = helper.getDogList();
        CustomListViewAdapter adapter = new CustomListViewAdapter(
                this,
                R.layout.custom_listview_item,
                dogList
        );
        listView.setAdapter(adapter);
    }
}
