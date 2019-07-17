package net.koreate.test_20190716_recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GridRecyclerViewAdapter
extends RecyclerView.Adapter<GridRecyclerViewAdapter.GridViewHolder>{

    ArrayList<RecyclerTestVO> list;

    public GridRecyclerViewAdapter(ArrayList<RecyclerTestVO> list){
        this.list = list;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GridViewHolder(
                    LayoutInflater.from(parent.getContext()
                    ).inflate(
                            R.layout.grid_custom_item,
                            parent,
                            false
                    )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
        final int pos = position;
        RecyclerTestVO rtv = list.get(pos);
        holder.textView.setText(rtv.getTitle());
        holder.imageView.setBackgroundResource(rtv.getImg());

        //삭제
        holder.textView.setOnClickListener(view->{
            list.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,getItemCount());
        });

        // 추가
        holder.imageView.setOnClickListener(view->{
            list.add(position,list.get(position));
            notifyItemInserted(position);
            notifyItemRangeChanged(position,getItemCount());
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class GridViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.textView)
        TextView textView;

        @BindView(R.id.imageView)
        ImageView imageView;

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
