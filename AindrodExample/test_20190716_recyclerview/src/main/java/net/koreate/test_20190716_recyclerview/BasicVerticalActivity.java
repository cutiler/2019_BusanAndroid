package net.koreate.test_20190716_recyclerview;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BasicVerticalActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindArray(R.array.img_title)
    String[] titles;

    @BindView(R.id.group)
    RadioGroup group;

    boolean isCheck = true;

    BasicVerticalRecyclerViewAdapter adapter;

    ArrayList<String> titleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_vertical);
        ButterKnife.bind(this);

        titleList = new ArrayList<>();
        for(String s : titles){
            titleList.add(s);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BasicVerticalRecyclerViewAdapter(titleList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new CustomItemDecoration(this));
        adapter.setOnItemClickListener(new BasicVerticalRecyclerViewAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if(isCheck){
                    // 아이템 추가
                    adapter.addItem(titleList.get(position),position);
                }else{
                    // 아이템 삭제
                    adapter.deleteItem(position);
                }
            }
        });

/*
      group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedID) {

            }
        });
*/

        group.setOnCheckedChangeListener((group, checkedId)->{
            if(checkedId == R.id.addBtn){
                isCheck = true;
            }else{
                isCheck = false;
            }
            System.out.println("isCheck : " + isCheck);
        });
    }
}
