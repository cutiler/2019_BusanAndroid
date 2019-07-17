package net.koreate.test_20190716_recyclerview;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HorizontalActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindArray(R.array.img_title)
    String[] titles;

    @BindArray(R.array.img_drawable)
    TypedArray imgs;

    ArrayList<RecyclerTestVO> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal);
        ButterKnife.bind(this);

        list = new ArrayList<>();

        for(int i=0; i<imgs.length(); i++){
           RecyclerTestVO rtv = new RecyclerTestVO();
           String title = titles[i];
           rtv.setTitle(title);
           int index = imgs.getResourceId(i,0);
           rtv.setImg(index);
            System.out.println(rtv);
           list.add(rtv);
        }

        HorizontalRecyclerViewAdapter adapter
                = new HorizontalRecyclerViewAdapter(list);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
        );
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);


    }
}
