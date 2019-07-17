package net.koreate.test_20190716_recyclerview;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GridActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindArray(R.array.img_title)
    String[] titles;

    @BindArray(R.array.img_drawable)
    TypedArray imgs;

    ArrayList<RecyclerTestVO> list;

    GridLayoutManager manager;
    GridRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        ButterKnife.bind(this);

        list = new ArrayList<>();
        for(int i=0; i<imgs.length(); i++){
            RecyclerTestVO rtv = new RecyclerTestVO();
            rtv.setTitle(titles[i]);
            rtv.setImg(imgs.getResourceId(i,0));
            list.add(rtv);
        }

        manager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(manager);
        adapter = new GridRecyclerViewAdapter(list);
        recyclerView.setAdapter(adapter);

    }
}
