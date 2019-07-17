package net.koreate.test_20190716_adapterview.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import net.koreate.test_20190716_adapterview.vo.DogVO;

import java.util.ArrayList;

public class CustomListViewAdapter extends ArrayAdapter<DogVO> {

    Context context;
    int resId;
    ArrayList<DogVO> dogList;

    public CustomListViewAdapter(Context context, int resource, ArrayList<DogVO> dogList) {
        super(context, resource);
        this.context=context;
        this.resId = resource;
        this.dogList = dogList;
    }

    @Override
    public int getCount() {
        return dogList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater li
            = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(resId,null);
            DogViewHolder holder = new DogViewHolder(convertView);
            convertView.setTag(holder);
        }

        DogViewHolder holder = (DogViewHolder)convertView.getTag();
        DogVO dog = dogList.get(position);
        holder.id.setText(String.valueOf(dog.get_id()));
        holder.kind.setText(dog.getKind());
        holder.name.setText(dog.getName());
        holder.menu.setOnClickListener(view ->{
            Toast.makeText(
                    context,
                    "select menu btn : "+dog.getName(),
                    Toast.LENGTH_SHORT
            ).show();
        });

        return convertView;
    }
}
