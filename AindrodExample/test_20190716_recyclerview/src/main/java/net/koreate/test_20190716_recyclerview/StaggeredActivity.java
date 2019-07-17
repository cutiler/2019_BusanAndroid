package net.koreate.test_20190716_recyclerview;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StaggeredActivity extends AppCompatActivity {

    @BindArray(R.array.img_title) String[] titles;
    @BindArray(R.array.img_drawable) TypedArray imgs;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    ArrayList<RecyclerTestVO> list;

    StaggeredGridLayoutManager manager;
    StaggeredRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staggered);
        ButterKnife.bind(this);

        list = new ArrayList<>();
        for(int i=0; i<titles.length; i++){
            RecyclerTestVO rtv = new RecyclerTestVO();
            rtv.setTitle(titles[i]);
            rtv.setImg(imgs.getResourceId(i,0));
            list.add(rtv);
        }

        manager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new StaggeredRecyclerViewAdapter(list);
        recyclerView.setAdapter(adapter);




    }

}
