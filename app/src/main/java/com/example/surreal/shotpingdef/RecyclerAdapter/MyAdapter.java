package com.example.surreal.shotpingdef.RecyclerAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.surreal.shotpingdef.R;

public class MyAdapter extends RecyclerView.Adapter <MyAdapter.MyHolder> {

    private Context context;
    private int images[];

    public MyAdapter(Context context, int[] images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_layot, null);

        MyHolder myHolder = new MyHolder(layout);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder viewHolder, int i) {

        viewHolder.img.setImageResource(images[i]);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {

        ImageView img;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
