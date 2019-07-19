package net.koreate.test_20190719_network_valley.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.koreate.test_20190719_network_valley.R;
import net.koreate.test_20190719_network_valley.vo.BoardVO;

import java.util.ArrayList;

public class CustomListViewAdapter extends BaseAdapter {

    ArrayList<BoardVO>list;
    int layout;

    public CustomListViewAdapter(ArrayList<BoardVO>list, int layout){
        this.list=list;
        this.layout=layout;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getBno();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if(view == null){
            view
                = ((LayoutInflater)viewGroup.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(layout,null);
        }

        TextView bno = view.findViewById(R.id.bno);
        ImageView img = view.findViewById(R.id.img);
        TextView content = view.findViewById(R.id.content);
        TextView name = view.findViewById(R.id.name);

        BoardVO board = list.get(position);
        bno.setText(String.valueOf(board.getBno()));
        content.setText(board.getContent());
        name.setText(board.getName());

        String imgUrl = board.getImg();
        String imgPath = URLConfig.BASE+imgUrl;
        System.out.println(imgPath);

        Glide.with(img.getContext()).load(imgPath).into(img);

        return view;
    }
}
