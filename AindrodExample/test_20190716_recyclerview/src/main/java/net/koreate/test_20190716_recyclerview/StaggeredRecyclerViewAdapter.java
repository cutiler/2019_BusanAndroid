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

public class StaggeredRecyclerViewAdapter
        extends RecyclerView.Adapter<StaggeredRecyclerViewAdapter.StaggeredViewHolder>{

    ArrayList<RecyclerTestVO> list;

    public StaggeredRecyclerViewAdapter(ArrayList<RecyclerTestVO> list){
        this.list = list;
    }

    @NonNull
    @Override
    public StaggeredViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StaggeredViewHolder(LayoutInflater.from(
                parent.getContext()
        ).inflate(
                R.layout.staggered_custom_item,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull StaggeredViewHolder holder, int position) {
        holder.textView.setText(list.get(position).getTitle());
        holder.imageView.setBackgroundResource(list.get(position).getImg());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class StaggeredViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.textView) TextView textView;
        @BindView(R.id.imageView) ImageView imageView;

        public StaggeredViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
