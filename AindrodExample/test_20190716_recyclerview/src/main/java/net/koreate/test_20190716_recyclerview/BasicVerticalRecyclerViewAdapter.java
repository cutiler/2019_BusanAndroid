package net.koreate.test_20190716_recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BasicVerticalRecyclerViewAdapter extends RecyclerView.Adapter<BasicVerticalRecyclerViewAdapter.TextHolder> {

    List<String> list;

    static MyClickListener myCLickListener;

    public interface MyClickListener{
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(BasicVerticalRecyclerViewAdapter.MyClickListener listener){
        this.myCLickListener = listener;
    }

    public BasicVerticalRecyclerViewAdapter(List<String> list){
        this.list = list;
    }


    @NonNull
    @Override
    public TextHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root =
                LayoutInflater.from(parent.getContext()).inflate(
                        android.R.layout.simple_list_item_1,
                        parent,
                        false
                );
        return new TextHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull TextHolder holder, int position) {
        String s = list.get(position);
        holder.textView.setText(s);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(String data, int position){
        list.add(position,data);
        notifyItemInserted(position);
        notifyItemRangeChanged(position,getItemCount());
    }

    public void deleteItem(int position){
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,getItemCount());
    }




    static class TextHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;
        public TextHolder(@NonNull View root) {
            super(root);
            textView = itemView.findViewById(android.R.id.text1);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            myCLickListener.onItemClick(getAdapterPosition(),view);
        }
    }

}
