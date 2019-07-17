package net.koreate.test_20190716_recyclerview;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MultipleRecyclerViewActivity extends AppCompatActivity {

    @BindView(R.id.verticalRecyclerView)
    RecyclerView verticalRecyclerView;
    @BindView(R.id.horizontalRecyclerView)
    RecyclerView horizontalRecyclerView;
    @BindView(R.id.staggeredRecyclerView)
    RecyclerView staggeredRecyclerView;

    @BindArray(R.array.img_title) String[] titles;
    @BindArray(R.array.img_drawable) TypedArray imgs;

    ArrayList<String> verticalList;
    ArrayList<RecyclerTestVO> horizontalList;
    ArrayList<RecyclerTestVO> staggeredList;

    boolean isCheck = true;

    BasicVerticalRecyclerViewAdapter verticalAdapter;
    HorizontalRecyclerViewAdapter horizontalAdapter;
    StaggeredRecyclerViewAdapter staggeredAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_recycler_view);
        ButterKnife.bind(this);
        initData();
        initView();
    }
    private void initData() {
        verticalList = new ArrayList<>();
        horizontalList = new ArrayList<>();
        staggeredList = new ArrayList<>();
        for(int i = 0 ; i< imgs.length(); i++){
            verticalList.add(titles[i]);
            RecyclerTestVO rtv = new RecyclerTestVO();
            rtv.setTitle(titles[i]);
            rtv.setImg(imgs.getResourceId(i,0));
            horizontalList.add(rtv);

            rtv = new RecyclerTestVO();
            rtv.setTitle(titles[i]);
            rtv.setImg(imgs.getResourceId(i,0));
            staggeredList.add(rtv);
        }
    }


    private void initView() {
        verticalRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        verticalAdapter = new BasicVerticalRecyclerViewAdapter(verticalList);
        verticalRecyclerView.setAdapter(verticalAdapter);
        verticalRecyclerView.addItemDecoration(new CustomItemDecoration(this));
        verticalAdapter.setOnItemClickListener(new BasicVerticalRecyclerViewAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if(isCheck){
                    verticalAdapter.deleteItem(position);
                    isCheck = false;
                }else{
                    verticalAdapter.addItem("추가된아이템",position);
                    isCheck = true;
                }
            }
        });


        horizontalRecyclerView.setLayoutManager(
            new LinearLayoutManager(
                    this,
                    LinearLayoutManager.HORIZONTAL,
                    false)
        );
        horizontalAdapter = new HorizontalRecyclerViewAdapter(horizontalList);
        horizontalRecyclerView.setAdapter(horizontalAdapter);

        staggeredRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(
                        3,
                        StaggeredGridLayoutManager.VERTICAL
                )
        );
        staggeredAdapter = new StaggeredRecyclerViewAdapter(staggeredList);
        staggeredRecyclerView.setAdapter(staggeredAdapter);
    }
}
