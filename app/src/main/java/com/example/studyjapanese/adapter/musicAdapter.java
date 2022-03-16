package com.example.studyjapanese.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studyjapanese.R;

import java.util.ArrayList;

public class musicAdapter extends RecyclerView.Adapter<musicAdapter.musicHolder> {
    ArrayList<MusicRecyclerViewItem> list;

    public musicAdapter(ArrayList<MusicRecyclerViewItem> data){
        list = data;
    }

    @NonNull
    @Override
    public musicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context =parent.getContext();
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.music_recyclerview_item_list, parent, false);
        musicAdapter.musicHolder mh = new musicAdapter.musicHolder(view);
        return mh;
    }

    @Override
    public void onBindViewHolder(@NonNull musicHolder holder, int position) {
        MusicRecyclerViewItem item = list.get(position);
        holder.item_list_textView1.setText(item.getMain());
        holder.item_list_textView2.setText(item.getSub());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //아이템 뷰를 저장하는 뷰홀더 클래스스
   class musicHolder extends RecyclerView.ViewHolder{
        TextView item_list_textView1;
        TextView item_list_textView2;
        public musicHolder(@NonNull View itemView) {
            super(itemView);
            item_list_textView1 = itemView.findViewById(R.id.item_list_textView1);
            item_list_textView2 = itemView.findViewById(R.id.item_list_textView2);
        }
    }
}

