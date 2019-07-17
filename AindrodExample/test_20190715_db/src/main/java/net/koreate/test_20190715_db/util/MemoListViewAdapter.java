package net.koreate.test_20190715_db.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.koreate.test_20190715_db.R;
import net.koreate.test_20190715_db.vo.MemoVO;

import java.util.ArrayList;

public class MemoListViewAdapter extends BaseAdapter {

    Context context ;
    ArrayList<MemoVO> memoList;
    int layout;
    LayoutInflater li;

    SQLiteMemoDBHelper helper;

    public MemoListViewAdapter
            (Context context,
             ArrayList<MemoVO> memoList,
             int layout){
        this.context = context;
        this.memoList = memoList;
        this.layout = layout;
        li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        helper = new SQLiteMemoDBHelper(context);

    }


    @Override
    public int getCount() {
        return memoList.size();
    }

    @Override
    public Object getItem(int position) {
        return memoList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return memoList.get(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view  == null){
            view = this.li.inflate(layout,viewGroup,false);
        }
        MemoVO memo = memoList.get(position);

        // textId // textTitle // textContent
        TextView textId = view.findViewById(R.id.textId);
        TextView textTitle = view.findViewById(R.id.textTitle);
        TextView textContent = view.findViewById(R.id.textContent);

        textId.setText(String.valueOf(memo.getId()));
        textTitle.setText(memo.getTitle());
        textContent.setText(memo.getContent());

        ImageView closeBtn = view.findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(v ->{
            // adapter 사용하는 데이터를 변경
            memoList.remove(position);
            notifyDataSetChanged();
            // DB 에 데이터 삭제
            helper.deleteMemo(memo.getId());
        });
        return view;
    }
}
